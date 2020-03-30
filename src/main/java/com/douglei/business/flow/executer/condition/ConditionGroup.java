package com.douglei.business.flow.executer.condition;

import com.douglei.business.flow.executer.LogicalOP;

/**
 * 
 * @author DougLei
 */
public class ConditionGroup {
	private byte index;
	private Condition[] conditions;
	private LogicalOP nextOP;
	
	public ConditionGroup(byte length) {
		conditions = new Condition[length];
	}
	
	public void addCondition(Condition condition) {
		if(index < conditions.length)
			conditions[index++] = condition;
	}
	
	public void setNextOP(LogicalOP op) {
		this.nextOP = op;
	}
	
}
