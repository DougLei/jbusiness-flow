package com.douglei.business.flow.executer.action.impl.func;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.business.flow.executer.ParameterContext;
import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.action.ExecuteParameter;
import com.douglei.business.flow.executer.parameter.DeclaredParameter;
import com.douglei.business.flow.executer.parameter.Parameter;

/**
 * 
 * @author DougLei
 */
public class FuncLoopAction extends Action {
	private static final long serialVersionUID = -2746871227926573838L;
	private static final Logger logger = LoggerFactory.getLogger(FuncLoopAction.class);
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
	public Object execute(ExecuteParameter executeParameter) {
		if(logger.isDebugEnabled())
			logger.debug("执行[{}]", getClass().getName());
		Object value = ParameterContext.getValue(collection);
		if(value != null) {
			if(value instanceof Collection) {
				Collection<Object> list = (Collection<Object>)value;
				if(list.size() > 0) {
					ParameterContext.addParameter(alias, null);
					for (Object lv : list)
						executeCore(lv, executeParameter);
				}
			}else  if(value.getClass().isArray()) {
				Object[] list = (Object[]) value;
				if(list.length > 0) {
					ParameterContext.addParameter(alias, null);
					for (Object lv : list)
						executeCore(lv, executeParameter);
				}
			}
		}
		return null;
	}
	private void executeCore(Object lv, ExecuteParameter executeParameter) {
		ParameterContext.getParameter(aliasParameter).updateValue(lv);
		for (Action action : actions) {
			action.execute(executeParameter);
		}
	}
}
