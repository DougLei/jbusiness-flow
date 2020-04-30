package com.douglei.business.flow.executer.sql.core;

import com.douglei.business.flow.executer.action.ExecuteParameter;
import com.douglei.business.flow.executer.parameter.DeclaredParameter;
import com.douglei.business.flow.executer.sql.Sql;
import com.douglei.business.flow.executer.sql.SqlData;
import com.douglei.business.flow.executer.sql.component.Component;
import com.douglei.business.flow.executer.sql.component.Table;
import com.douglei.business.flow.executer.sql.component.insert.Column;

/**
 * 
 * @author DougLei
 */
public class InsertSql extends Sql {
	public static final String TYPE = "insert";
	public static final byte VALUES_TYPE_VALUE = 0; // values的类型: 值
	public static final byte VALUES_TYPE_SELECT = 1; // values的类型: 子查询
	
	private Table table;
	private Column[] columns;
	private byte valuesType;
	private Component[] values; // 可能是value数组, 也可能是select
	
	public InsertSql(String name, DeclaredParameter[] parameters, byte valuesType) {
		super(name, parameters);
		this.valuesType = valuesType;
	}
	
	public void setTable(Table table) {
		this.table = table;
	}
	public void setColumns(Column[] columns) {
		this.columns = columns;
	}
	public void setValues(Component[] values) {
		this.values = values;
	}
	public byte getValuesType() {
		return valuesType;
	}

	@Override
	protected Object invokeCore(ExecuteParameter executeParameter) {
		SqlData sqlData = new SqlData("INSERT INTO ", executeParameter);
		table.append2SqlData(sqlData);
		appendColumns2SqlData(sqlData);
		appendValues2SqlData(sqlData);
		return executeParameter.getSession().executeUpdate(sqlData.getSql(), sqlData.getParameterValues());
	}
	
	// columns
	private void appendColumns2SqlData(SqlData sqlData) {
		if(columns != null)
			Component.appendComponents2SqlData("(", ")", columns, sqlData);
	}
	
	// values
	private void appendValues2SqlData(SqlData sqlData) {
		if(valuesType == VALUES_TYPE_VALUE) {
			Component.appendComponents2SqlData(" values(", ")", values, sqlData);
		}else if(valuesType == VALUES_TYPE_SELECT) {
			Component.appendComponents2SqlData(" ", null, values, sqlData);
		}
	}
}
