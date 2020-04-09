package com.douglei.business.flow.executer.sql.component.select.condition;

import com.douglei.business.flow.executer.LogicalOP;
import com.douglei.business.flow.executer.sql.SqlData;
import com.douglei.business.flow.executer.sql.component.Component;
import com.douglei.business.flow.executer.sql.component.Value;

/**
 * 
 * @author DougLei
 */
public class Condition implements Component{
	private Value left;
	private Value[] rights;
	private CompareType cop;
	private LogicalOP op;
	
	public Condition(Value left, CompareType cop, LogicalOP op) {
		this.left = left;
		this.cop = cop;
		this.op = op;
	}

	public void setRights(Value[] rights) {
		this.rights = rights;
	}
	public boolean opIsNULL() {
		return cop == CompareType.NULL || cop == CompareType.NNULL;
	}
	
	@Override
	public void append2SqlData(SqlData sqlData) {
		cop.append2SqlData(left, rights, sqlData);
	}

	@Override
	public String linkSymbol() {
		return op.name();
	}
}


