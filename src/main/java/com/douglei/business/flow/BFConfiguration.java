package com.douglei.business.flow;

/**
 * 业务流配置器
 * @author DougLei
 */
public class BFConfiguration {
	private Database database; // 根据数据库不同, 可能对sql有不同的处理

	public Database getDatabase() {
		return database;
	}
	public void setDatabase(Database database) {
		this.database = database;
	}
}
