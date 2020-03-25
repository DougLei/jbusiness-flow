package com.douglei.business.flow.executer.action.impl.func;

import java.util.Collection;

import com.douglei.business.flow.executer.ParameterContext;
import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.parameter.Parameter;

/**
 * 
 * @author DougLei
 */
public class FuncLoopAction extends Action {
	private Parameter collection;
	private Parameter alias;
	private Action[] actions;
	
	public FuncLoopAction(Parameter collection, Parameter alias, Action[] actions) {
		this.collection = collection;
		this.alias = alias;
		this.actions = actions;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Object execute() {
		Object value = ParameterContext.getValue(collection);
		if(value != null) {
			if(value instanceof Collection<?>) {
				executeList((Collection<Object>)value);
			}
		}
		return null;
	}
	private void executeList(Collection<Object> list) {
		if(list.size() > 0) {
			ParameterContext.addParameter(alias, null);
			for (Object value : list) {
				ParameterContext.updateValue(alias, value);
				for (Action action : actions) {
					action.execute();
				}
			}
		}
	}
}
