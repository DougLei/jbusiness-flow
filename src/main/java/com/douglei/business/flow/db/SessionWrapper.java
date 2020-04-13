package com.douglei.business.flow.db;

import java.util.List;
import java.util.Map;

/**
 * session包装器, 用来包装对数据库访问的实例
 * @author DougLei
 */
public interface SessionWrapper {

	/**
	 * 插入数据
	 * @param sql
	 * @param values
	 * @return 影响的数据条数
	 */
	int insert(String sql, List<Object> values);
	
	/**
	 * 删除数据
	 * @param sql
	 * @param values
	 * @return 影响的数据条数
	 */
	int delete(String sql, List<Object> values);

	/**
	 * 更新数据
	 * @param sql
	 * @param values
	 * @return 影响的数据条数
	 */
	int update(String sql, List<Object> values);

	/**
	 * 查询数据
	 * @param sql
	 * @param values
	 * @return 结果集
	 */
	List<Map<String, Object>> query(String sql, List<Object> values);
}
