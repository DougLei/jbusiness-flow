package com.douglei.business.flow.executer.sql;

import com.douglei.business.flow.db.Session;
import com.douglei.business.flow.executer.parameter.Parameter;
import com.douglei.business.flow.executer.sql.component.Component;
import com.douglei.business.flow.executer.sql.component.Table;

/**
 * 
 * @author DougLei
 */
public class InsertSql extends Sql {
	public static final byte TYPE = 1;
	public static final byte VALUES_TYPE_VALUE = 0; // values的类型: 值
	public static final byte VALUES_TYPE_SELECT = 1; // values的类型: 子查询
	
	private Table table;
	private String[] columns;
	private byte valuesType;
	private Component[] values; // 可能是value数组, 也可能是select
	
	public InsertSql(String name, Parameter[] parameters, byte valuesType) {
		super(name, parameters);
		this.valuesType = valuesType;
	}
	
	public void setTable(Table table) {
		this.table = table;
	}
	public void setColumns(String[] columns) {
		this.columns = columns;
	}
	public void setValues(Component[] values) {
		this.values = values;
	}
	public byte getValuesType() {
		return valuesType;
	}

	@Override
	protected Object invokeCore(Session session) {
		SqlData sqlData = new SqlData("INSERT INTO ");
		table.append2SqlData(sqlData);
		appendColumns2SqlData(sqlData);
		appendValues2SqlData(sqlData);

		return session.insert(sqlData.getSql(), sqlData.getParameterValues());
	}

	private void appendColumns2SqlData(SqlData sqlData) {
		if(columns != null) {
			sqlData.appendSql('(');
			for(int i=0;i<columns.length;i++) {
				sqlData.appendSql(columns[i]);
				if(i < columns.length-1)
					sqlData.appendSql(',');
			}
			sqlData.appendSql(')');
		}
	}
	
	private void appendValues2SqlData(SqlData sqlData) {
		sqlData.appendSql(' ');
		if(valuesType == VALUES_TYPE_VALUE) {
			sqlData.appendSql("values(");
			Component.appendComponents2SqlData(values, sqlData);
			sqlData.appendSql(')');
		}else if(valuesType == VALUES_TYPE_SELECT && values != null) {
			Component.appendComponents2SqlData(values, sqlData);
		}
	}
}
