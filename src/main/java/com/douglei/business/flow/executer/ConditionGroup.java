package com.douglei.business.flow.executer;

/**
 * 
 * @author DougLei
 */
public class ConditionGroup {
	private Condition[] conditions;
	/**
	 * {@link Condition#CONDITION_AND}
	 * {@link Condition#CONDITION_OR}
	 */
	private byte op; 
	
	public ConditionGroup(Condition[] conditions, byte op) {
		this.conditions = conditions;
		this.op = op;
	}


	public boolean execute() {
		// TODO 
		return false;
	}
}
