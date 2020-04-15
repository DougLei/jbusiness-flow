package com.douglei.business.flow.executer.sql;

import com.douglei.business.flow.db.DBSession;
import com.douglei.business.flow.executer.parameter.Parameter;
import com.douglei.business.flow.executer.sql.component.Component;
import com.douglei.business.flow.executer.sql.component.Table;
import com.douglei.business.flow.executer.sql.component.select.condition.ConditionGroups;
import com.douglei.business.flow.executer.sql.component.update.Set;

/**
 * 
 * @author DougLei
 */
public class UpdateSql extends Sql {
	public static final String TYPE = "update"; 

	private Table table;
	private Set[] sets;
	private ConditionGroups whereGroups;
	
	public UpdateSql(String name, Parameter[] parameters) {
		super(name, parameters);
	}

	public void setTable(Table table) {
		this.table = table;
	}
	public void setSets(Set[] sets) {
		this.sets = sets;
	}
	public void setWhereGroups(ConditionGroups whereGroups) {
		this.whereGroups = whereGroups;
	}

	@Override
	protected Object invokeCore(DBSession session) {
		SqlData sqlData = new SqlData("UPDATE ");
		table.append2SqlData(sqlData);
		appendSets2SqlData(sqlData);
		whereGroups.append2SqlData(sqlData);
		return session.executeUpdate(sqlData.getSql(), sqlData.getParameterValues());
	}

	private void appendSets2SqlData(SqlData sqlData) {
		sqlData.appendSql(" SET ");
		Component.appendComponents2SqlData(sets, sqlData);
	}
}
