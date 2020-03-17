package com.douglei.business.flow.executer.sql.component.select;

import com.douglei.business.flow.executer.LogicalOP;

/**
 * 
 * @author DougLei
 */
public class ConditionGroup {
	protected Condition[] conditions;
	protected LogicalOP op;
	
	public ConditionGroup(Condition[] conditions, LogicalOP op) {
		this.conditions = conditions;
		this.op = op;
	}
}
