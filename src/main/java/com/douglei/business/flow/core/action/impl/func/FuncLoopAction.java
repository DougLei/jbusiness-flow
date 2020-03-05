package com.douglei.business.flow.core.action.impl.func;

import com.douglei.business.flow.core.Parameter;
import com.douglei.business.flow.core.action.Action;
import com.douglei.tools.utils.StringUtil;

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
		this.alias = StringUtil.isEmpty(alias)?collection.getName():alias;
		this.actions = actions;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}
}
