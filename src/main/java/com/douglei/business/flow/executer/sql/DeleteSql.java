package com.douglei.business.flow.executer.sql;

import com.douglei.business.flow.executer.parameter.Parameter;
import com.douglei.business.flow.executer.sql.component.Table;
import com.douglei.business.flow.executer.sql.component.select.ConditionGroup;

/**
 * 
 * @author DougLei
 */
public class DeleteSql extends Sql {
	public static final byte TYPE = 2; // sql类型: delete

	private Table table;
	private ConditionGroup[] whereGroups;
	
	public DeleteSql(String name, String description, Parameter[] parameters) {
		super(name, description, parameters);
	}

	public void setTable(Table table) {
		this.table = table;
	}
	public void setWhereGroups(ConditionGroup[] whereGroups) {
		this.whereGroups = whereGroups;
	}
}
