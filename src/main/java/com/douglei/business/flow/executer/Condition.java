package com.douglei.business.flow.executer;

import com.douglei.business.flow.executer.action.Action;

/**
 * 
 * @author DougLei
 */
public class Condition extends ConditionBase{
	private Action dataOpCompareAction;
	
	public Condition(boolean inverse, LogicalOP op, Action dataOpCompareAction) {
		super(inverse, op);
		this.dataOpCompareAction = dataOpCompareAction;
	}
	
	@Override
	public boolean validate() {
		boolean result = (boolean) dataOpCompareAction.execute();
		if(inverse) {
			return !result;
		}
		return result;
	}
}
