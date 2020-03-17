package com.douglei.business.flow.executer;

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
		
		boolean result = ConditionUtil.validate(conditions, conditions[0].validate(), conditions[0].getOp(), 1);
		if(inverse) {
			return !result;
		}
		return result;
	}
}
