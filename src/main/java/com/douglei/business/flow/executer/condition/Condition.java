package com.douglei.business.flow.executer.condition;

import com.douglei.business.flow.executer.action.Action;

/**
 * 
 * @author DougLei
 */
public class Condition extends ConditionValidator{
	private boolean inverse;
	private Action dataOpCompareAction;
	
	public Condition(boolean inverse, Action dataOpCompareAction) {
		this.inverse = inverse;
		this.dataOpCompareAction = dataOpCompareAction;
	}

}
