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
		if(value != null && value instanceof Collection<?>) {
			Collection<Object> list = (Collection<Object>)value;
			if(list.size() > 0) {
				ParameterContext.addParameter(alias, null);
				for (Object lv : list) {
					ParameterContext.updateValue(alias, lv);
					for (Action action : actions) {
						action.execute();
					}
				}
			}
		}
		return null;
	}
}
