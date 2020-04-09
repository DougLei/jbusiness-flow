package com.douglei.business.flow.executer.sql.component.select.go;

/**
 * 
 * @author DougLei
 */
public enum GOType {
	GROUP_BY("groupBys"),
	ORDER_BY("orderBys");
	
	private String prefixSql;
	private String jsonKey;
	private GOType(String jsonKey) {
		this.jsonKey = jsonKey;
		this.prefixSql = " "+name().replace('_', ' ')+" ";
	}
	
	public String getPrefixSql() {
		return prefixSql;
	}
	public String getJsonKey() {
		return jsonKey;
	}
}
