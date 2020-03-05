package com.douglei.business.flow.executer.action.impl.func;

import com.douglei.business.flow.executer.Parameter;
import com.douglei.business.flow.executer.action.Action;

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
	public void execute() {
		// TODO Auto-generated method stub

	}
}
