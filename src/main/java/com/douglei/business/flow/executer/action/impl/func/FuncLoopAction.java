package com.douglei.business.flow.executer.action.impl.func;

import java.util.Map;

import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.parameter.Parameter;

/**
 * 
 * @author DougLei
 */
public class FuncLoopAction extends Action {
	private Parameter collection;
	private String alias;
	private Action[] actions;
	
	public FuncLoopAction(Parameter collection, String alias, Action[] actions) {
		this.collection = collection;
		this.alias = alias;
		this.actions = actions;
	}

	@Override
	public Object execute(Map<String, Parameter> localParameterMap) {
		// TODO Auto-generated method stub
		return null;
	}
}
