package com.douglei.business.flow.executer.condition;

import com.douglei.business.flow.executer.LogicalOP;

/**
 * 
 * @author DougLei
 */
public class ConditionGroup extends ConditionBase{
	private Condition[] conditions;
	
	public ConditionGroup(boolean inverse, LogicalOP op, Condition[] conditions) {
		super(inverse, op);
		this.conditions = conditions;
	}
	
	@Override
	public boolean validate() {
		if(conditions.length == 0) {
			return true;
		}
		
		boolean result = validate(conditions[0].validate(), conditions[0].getOp(), 1, conditions);
		if(inverse) {
			return !result;
		}
		return result;
	}
}
