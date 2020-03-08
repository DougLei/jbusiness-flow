package com.douglei.business.flow.executer.condition;

/**
 * 
 * @author DougLei
 */
public class ConditionGroup {
	private Condition[] conditions;
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
