package com.douglei.business.flow.executer.sql.component.select;

import com.douglei.business.flow.executer.LogicalOP;
import com.douglei.business.flow.executer.sql.component.Value;

/**
 * 
 * @author DougLei
 */
public class Condition {
	protected Value left;
	protected Value[] rights;
	protected CompareType cop;
	protected LogicalOP op;
	
	public Condition(Value left, CompareType cop, LogicalOP op) {
		this.left = left;
		this.cop = cop;
		this.op = op;
	}

	public void setRights(Value[] rights) {
		this.rights = rights;
	}
	public CompareType getCop() {
		return cop;
	}
}


