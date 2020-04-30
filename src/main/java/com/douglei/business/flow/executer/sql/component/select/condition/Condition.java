package com.douglei.business.flow.executer.sql.component.select.condition;

import com.douglei.business.flow.executer.LogicalOP;
import com.douglei.business.flow.executer.condition.ConditionValidator;
import com.douglei.business.flow.executer.sql.SqlData;
import com.douglei.business.flow.executer.sql.component.Component;
import com.douglei.business.flow.executer.sql.component.Value;

/**
 * 
 * @author DougLei
 */
public class Condition extends Component{
	private Value left;
	private Value[] rights;
	private CompareType cop;
	private LogicalOP op;
	
	public Condition(ConditionValidator validator, Value left, CompareType cop, LogicalOP op) {
		super(validator);
		this.left = left;
		this.cop = cop;
		this.op = op;
	}

	public void setRights(Value[] rights) {
		this.rights = rights;
		this.cop = cop.correct(rights.length);
	}
	
	/**
	 * 不是null或not null的比较操作符
	 * @return
	 */
	public boolean unNullCompareOP() {
		return cop != CompareType.NULL && cop != CompareType.NNULL;
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


