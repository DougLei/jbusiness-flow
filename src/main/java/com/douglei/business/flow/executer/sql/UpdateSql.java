package com.douglei.business.flow.executer.sql;

import com.douglei.business.flow.executer.parameter.Parameter;
import com.douglei.business.flow.executer.sql.component.Table;
import com.douglei.business.flow.executer.sql.component.Value;
import com.douglei.business.flow.executer.sql.component.select.ConditionGroups;

/**
 * 
 * @author DougLei
 */
public class UpdateSql extends Sql {
	public static final byte TYPE = 3; // sql类型: update

	private Table table;
	private Value[] sets;
	private ConditionGroups whereGroups;
	
	public UpdateSql(String name, Parameter[] parameters) {
		super(name, parameters);
	}

	public void setTable(Table table) {
		this.table = table;
	}
	public void setSets(Value[] sets) {
		this.sets = sets;
	}
	public void setWhereGroups(ConditionGroups whereGroups) {
		this.whereGroups = whereGroups;
	}

	@Override
	protected Object invokeCore() {
		SqlData sqlData = new SqlData();
		// TODO Auto-generated method stub
		
		
		
		return null;
	}
}
