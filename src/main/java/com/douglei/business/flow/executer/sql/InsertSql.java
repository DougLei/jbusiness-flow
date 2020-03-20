package com.douglei.business.flow.executer.sql;

import com.douglei.business.flow.executer.parameter.Parameter;
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
	private Object values; // 可能是value数组, 也可能是select
	
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
	public void setValues(Object values) {
		this.values = values;
	}
	public byte getValuesType() {
		return valuesType;
	}
}
