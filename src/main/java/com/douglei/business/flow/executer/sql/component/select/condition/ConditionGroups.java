package com.douglei.business.flow.executer.sql.component.select.condition;

import com.douglei.business.flow.executer.sql.SqlData;
import com.douglei.business.flow.executer.sql.component.Component;

/**
 * 
 * @author DougLei
 */
public class ConditionGroups implements Component {
	private String prefixSql;
	private ConditionGroup[] groups;
	
	public ConditionGroups(String prefixSql, ConditionGroup[] groups) {
		this.prefixSql = prefixSql;
		this.groups = groups;
	}

	@Override
	public void append2SqlData(SqlData sqlData) {
		// TODO Auto-generated method stub
		
	}
}
