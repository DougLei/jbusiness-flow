package com.douglei.business.flow.executer.sql;

import com.douglei.business.flow.db.SessionWrapper;
import com.douglei.business.flow.executer.parameter.Parameter;
import com.douglei.business.flow.executer.sql.component.Table;
import com.douglei.business.flow.executer.sql.component.select.condition.ConditionGroups;

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
	protected Object invokeCore(SessionWrapper session) {
		SqlData sqlData = new SqlData("DELETE ");
		table.append2SqlData(sqlData);
		whereGroups.append2SqlData(sqlData);
		return session.delete(sqlData.getSql(), sqlData.getParameterValues());
	}
}
