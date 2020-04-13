package com.douglei.business.flow.db;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author DougLei
 */
public interface Session {

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
