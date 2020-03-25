package com.douglei.business.flow.executer.condition;

/**
 * 
 * @author DougLei
 */
public class ConditionValidator {
	private ConditionGroup[] conditionGroups;
	
	public ConditionValidator(ConditionGroup[] conditionGroups) {
		this.conditionGroups = conditionGroups;
	}
	
	public boolean validate() {
		if(conditionGroups.length == 0) {
			return true;
		}
		
		// TODO 进行验证
		return false;
	}
}
