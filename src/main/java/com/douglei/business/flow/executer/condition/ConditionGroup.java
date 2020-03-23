package com.douglei.business.flow.executer.condition;

import com.douglei.business.flow.executer.LogicalOP;

/**
 * 
 * @author DougLei
 */
public class ConditionGroup extends ConditionBase{
	private ConditionGroup[] conditionGroups;
	private Condition[] conditions;
	private LogicalOP cgcop;
	
	public ConditionGroup(boolean inverse, LogicalOP op, ConditionGroup[] conditionGroups, Condition[] conditions, LogicalOP cgcop) {
		super(inverse, op);
		this.conditionGroups = conditionGroups;
		this.conditions = conditions;
		this.cgcop = cgcop;
	}
	
	@Override
	public boolean validate() {
		if(conditionGroups.length == 0 && conditions.length == 0) {
			return true;
		}
		
		boolean result = true;
		if(conditionGroups.length > 0) {
			result = validate(conditionGroups[0].validate(), conditionGroups[0].getOp(), 1, conditionGroups);
		}
		
		if(conditions.length > 0) {
			result = validate(result, cgcop, 0, conditions);
		}
		
		if(inverse) {
			return !result;
		}
		return result;
	}
}
