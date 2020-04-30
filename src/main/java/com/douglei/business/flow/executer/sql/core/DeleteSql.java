package com.douglei.business.flow.executer.sql.core;

import com.douglei.business.flow.executer.action.ExecuteParameter;
import com.douglei.business.flow.executer.parameter.DeclaredParameter;
import com.douglei.business.flow.executer.sql.Sql;
import com.douglei.business.flow.executer.sql.SqlData;
import com.douglei.business.flow.executer.sql.component.Table;
import com.douglei.business.flow.executer.sql.component.select.condition.ConditionGroupWrapper;

/**
 * 
 * @author DougLei
 */
public class DeleteSql extends Sql {
	public static final String TYPE = "delete";

	private Table table;
	private ConditionGroupWrapper whereGroups;
	
	public DeleteSql(String name, DeclaredParameter[] parameters) {
		super(name, parameters);
	}

	public void setTable(Table table) {
		this.table = table;
	}
	public void setWhereGroups(ConditionGroupWrapper whereGroups) {
		this.whereGroups = whereGroups;
	}

	@Override
	protected Object invokeCore(ExecuteParameter executeParameter) {
		SqlData sqlData = new SqlData("DELETE ");
		table.append2SqlData(sqlData);
		whereGroups.append2SqlData(sqlData);
		return executeParameter.getSession().executeUpdate(sqlData.getSql(), sqlData.getParameterValues());
	}
}
