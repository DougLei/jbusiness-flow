package com.douglei.business.flow.executer.sql;

import com.douglei.business.flow.executer.parameter.Parameter;
import com.douglei.business.flow.executer.sql.component.Table;
import com.douglei.business.flow.executer.sql.component.select.ConditionGroups;

/**
 * 
 * @author DougLei
 */
public class DeleteSql extends Sql {
	public static final byte TYPE = 2; // sql类型: delete

	private Table table;
	private ConditionGroups whereGroups;
	
	public DeleteSql(String name, Parameter[] parameters) {
		super(name, parameters);
	}

	public void setTable(Table table) {
		this.table = table;
	}
	public void setWhereGroups(ConditionGroups whereGroups) {
		this.whereGroups = whereGroups;
	}

	@Override
	protected Object invokeCore() {
		SqlData sqlData = new SqlData("delete ");
		
		
		
		return null;
	}
}
