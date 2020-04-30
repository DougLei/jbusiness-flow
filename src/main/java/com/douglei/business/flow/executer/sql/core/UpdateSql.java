package com.douglei.business.flow.executer.sql.core;

import com.douglei.business.flow.executer.action.ExecuteParameter;
import com.douglei.business.flow.executer.parameter.DeclaredParameter;
import com.douglei.business.flow.executer.sql.Sql;
import com.douglei.business.flow.executer.sql.SqlData;
import com.douglei.business.flow.executer.sql.component.Component;
import com.douglei.business.flow.executer.sql.component.Table;
import com.douglei.business.flow.executer.sql.component.select.condition.ConditionGroupWrapper;
import com.douglei.business.flow.executer.sql.component.update.Set;

/**
 * 
 * @author DougLei
 */
public class UpdateSql extends Sql {
	public static final String TYPE = "update"; 

	private Table table;
	private Set[] sets;
	private ConditionGroupWrapper whereGroups;
	
	public UpdateSql(String name, DeclaredParameter[] parameters) {
		super(name, parameters);
	}

	public void setTable(Table table) {
		this.table = table;
	}
	public void setSets(Set[] sets) {
		this.sets = sets;
	}
	public void setWhereGroups(ConditionGroupWrapper whereGroups) {
		this.whereGroups = whereGroups;
	}

	@Override
	protected Object invokeCore(ExecuteParameter executeParameter) {
		SqlData sqlData = new SqlData("UPDATE ", executeParameter);
		table.append2SqlData(sqlData);
		appendSets2SqlData(sqlData);
		whereGroups.append2SqlData(sqlData);
		return executeParameter.getSession().executeUpdate(sqlData.getSql(), sqlData.getParameterValues());
	}

	private void appendSets2SqlData(SqlData sqlData) {
		Component.appendComponents2SqlData(" SET ", null, sets, sqlData);
	}
}
