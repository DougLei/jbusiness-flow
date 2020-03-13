package com.douglei.business.flow.executer.sql.component.select;

/**
 * 
 * @author DougLei
 */
public class ConditionGroup {
	protected Condition[] conditions;
	/**
	 * {@link Condition#CONDITION_AND}
	 * {@link Condition#CONDITION_OR}
	 */
	protected byte op;
	
	public ConditionGroup(Condition[] conditions, byte op) {
		this.conditions = conditions;
		this.op = op;
	}
}
