package com.douglei.business.flow.executer.sql.component.select.condition;

/**
 * 
 * @author DougLei
 */
public enum ConditionType {
	ON("onGroups"),
	WHERE("whereGroups"),
	HAVING("havingGroups");
	
	private String prefixSql;
	private String jsonKey;
	private ConditionType(String jsonKey) {
		this.jsonKey = jsonKey;
		this.prefixSql = " "+name()+" ";
	}
	
	public String getPrefixSql() {
		return prefixSql;
	}
	public String getJsonKey() {
		return jsonKey;
	}
}
