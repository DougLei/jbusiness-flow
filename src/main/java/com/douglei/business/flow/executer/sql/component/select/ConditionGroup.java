package com.douglei.business.flow.executer.sql.component.select;

import com.douglei.business.flow.executer.LogicalOP;

/**
 * 
 * @author DougLei
 */
public class ConditionGroup {
	private ConditionGroup[] conditionGroups;
	private Condition[] conditions;
	private LogicalOP cgcop;
	private LogicalOP op;
	
	public ConditionGroup(ConditionGroup[] conditionGroups, Condition[] conditions, LogicalOP cgcop, LogicalOP op) {
		this.conditionGroups = conditionGroups;
		this.conditions = conditions;
		this.cgcop = cgcop;
		this.op = op;
	}
}
