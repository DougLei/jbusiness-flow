package com.douglei.business.flow.executer.sql.component;

import com.douglei.business.flow.executer.sql.SqlData;

/**
 * 
 * @author DougLei
 */
public class Function implements Component{
	private String name; // 函数名
	private Value[] values;
	
	public Function(String name) {
		this.name = name;
	}
	public void setValues(Value[] values) {
		this.values = values;
	}
	
	@Override
	public void append2SqlData(SqlData sqlData) {
		sqlData.appendSql(name).appendSql('(');
		Component.appendComponents2SqlData(values, sqlData);
		sqlData.appendSql(')');
	}
}
