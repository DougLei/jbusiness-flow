package com.douglei.business.flow.executer.sql.component.select.condition;

/**
 * 
 * @author DougLei
 */
public enum ConditionType {
	ON(" ON ", "onGroups"),
	WHERE(" WHERE ", "whereGroups"),
	HAVING(" HAVING ", "havingGroups");
	
	private String prefixSql;
	private String jsonKey;
	private ConditionType(String prefixSql, String jsonKey) {
		this.prefixSql = prefixSql;
		this.jsonKey = jsonKey;
	}
	
	public String getPrefixSql() {
		return prefixSql;
	}
	public String getJsonKey() {
		return jsonKey;
	}
}
