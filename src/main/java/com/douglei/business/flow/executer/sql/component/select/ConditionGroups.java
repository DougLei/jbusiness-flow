package com.douglei.business.flow.executer.sql.component.select;

/**
 * 
 * @author DougLei
 */
public class ConditionGroups {
	private String prefixSql;
	private ConditionGroup[] groups;
	
	public ConditionGroups(String prefixSql, ConditionGroup[] groups) {
		this.prefixSql = prefixSql;
		this.groups = groups;
	}
}
