package com.douglei.business.flow.db;

import java.util.List;
import java.util.Map;

/**
 * session包装器, 用来包装对数据库访问的实例
 * @author DougLei
 */
public interface SessionWrapper {
	
	/**
	 * 是否自动提交, 默认为true
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
