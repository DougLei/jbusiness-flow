package com.douglei.business.flow;

/**
 * 业务流配置器
 * @author DougLei
 */
public class BFConfiguration {
	/**
	 * {@link Constants}
	 */
	private byte database; // 根据数据库不同, 可能对sql有不同的处理

	
	public byte getDatabase() {
		return database;
	}
	public void setDatabase(byte database) {
		this.database = database;
	}
}
