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
		
		boolean conditionGroupResult = true;
		if(conditionGroups.length > 0) {
			conditionGroupResult = validate(conditionGroups[0].validate(), conditionGroups[0].getOp(), 1, conditionGroups);
		}
		
		boolean conditionResult = true;
		if(conditions.length > 0) {
			conditionResult = validate(conditions[0].validate(), conditions[0].getOp(), 1, conditions);
		}
		
		boolean result = cgcop.operating(conditionGroupResult, conditionResult);
		if(inverse) {
			return !result;
		}
		return result;
	}
}
