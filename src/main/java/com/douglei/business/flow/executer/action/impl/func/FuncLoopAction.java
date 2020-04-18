package com.douglei.business.flow.executer.action.impl.func;

import java.util.Collection;

import com.douglei.business.flow.db.DBSession;
import com.douglei.business.flow.executer.ParameterContext;
import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.parameter.DeclaredParameter;
import com.douglei.business.flow.executer.parameter.Parameter;

/**
 * 
 * @author DougLei
 */
public class FuncLoopAction extends Action {
	private Parameter collection;
	private DeclaredParameter alias;
	private Parameter aliasParameter;
	private Action[] actions;
	
	public FuncLoopAction(Parameter collection, DeclaredParameter alias, Parameter aliasParameter, Action[] actions) {
		this.collection = collection;
		this.alias = alias;
		this.aliasParameter = aliasParameter;
		this.actions = actions;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Object execute(DBSession session) {
		Object value = ParameterContext.getValue(collection);
		if(value != null) {
			if(value instanceof Collection) {
				Collection<Object> list = (Collection<Object>)value;
				if(list.size() > 0) {
					ParameterContext.addParameter(alias, null);
					for (Object lv : list)
						executeCore(lv, session);
				}
			}else  if(value.getClass().isArray()) {
				Object[] list = (Object[]) value;
				if(list.length > 0) {
					ParameterContext.addParameter(alias, null);
					for (Object lv : list)
						executeCore(lv, session);
				}
			}
		}
		return null;
	}
	private void executeCore(Object lv, DBSession session) {
		ParameterContext.getParameter(aliasParameter).updateValue(lv);
		for (Action action : actions) {
			action.execute(session);
		}
	}
}
