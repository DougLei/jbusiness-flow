package com.douglei.business.flow.executer.sql.component.select.group.and.order;

/**
 * 
 * @author DougLei
 */
public enum GroupAndOrderType {
	GROUP_BY(" GROUP BY ", "groupBys"),
	ORDER_BY(" ORDER BY ", "orderBys");
	
	private String prefixSql;
	private String jsonKey;
	private GroupAndOrderType(String prefixSql, String jsonKey) {
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
