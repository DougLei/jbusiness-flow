package com.douglei.business.flow.executer.sql.component.update;

import com.douglei.business.flow.executer.sql.SqlData;
import com.douglei.business.flow.executer.sql.component.Component;
import com.douglei.business.flow.executer.sql.component.Value;

/**
 * 
 * @author DougLei
 */
public class Set extends Component{
	private String column;
	private Value value;
	
	public Set(String column, Value value) {
		this.column = column;
		this.value = value;
	}

	@Override
	public void append2SqlData(SqlData sqlData) {
		sqlData.appendSql(column).appendSql("=");
		value.append2SqlData(sqlData);
	}
}
