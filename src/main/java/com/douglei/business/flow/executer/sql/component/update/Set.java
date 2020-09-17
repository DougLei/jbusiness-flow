package com.douglei.business.flow.executer.sql.component.update;

import com.douglei.business.flow.executer.condition.ConditionValidator;
import com.douglei.business.flow.executer.sql.SqlData;
import com.douglei.business.flow.executer.sql.component.Value;
import com.douglei.business.flow.executer.sql.component.insert.Column;

/**
 * 
 * @author DougLei
 */
public class Set extends Column{
	private static final long serialVersionUID = -4953137083038385073L;
	private Value value;
	
	public Set(ConditionValidator validator, String column, Value value) {
		super(validator);
		this.column = column;
		this.value = value;
	}

	@Override
	public void append2SqlData(SqlData sqlData) {
		sqlData.appendSql(column).appendSql("=");
		value.append2SqlData(sqlData);
	}
}
