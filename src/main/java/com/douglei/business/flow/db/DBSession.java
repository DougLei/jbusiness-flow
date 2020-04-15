package com.douglei.business.flow.db;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author DougLei
 */
public interface DBSession {
	
	/**
	 * 是否自动提交
	 * @return
	 */
	default boolean autoCommit() {
		return true;
	}
	
	/**
	 * 增删改数据
	 * @param sql
	 * @param values
	 * @return 影响的数据条数
	 */
	int executeUpdate(String sql, List<Object> values);

	/**
	 * 查询数据
	 * @param sql
	 * @param values
	 * @return 结果集, Map中, key为列名, value为对应的值
	 */
	List<Map<String, Object>> query(String sql, List<Object> values);
	
	/**
	 * 提交
	 */
	void commit();
	
	/**
	 * 回滚
	 */
	void rollback();
	
	/**
	 * 关闭
	 */
	void close();
}
