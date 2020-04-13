package com.douglei.business.flow.db;

import java.util.List;
import java.util.Map;

import com.douglei.business.flow.executer.sql.SqlData;

/**
 * session包装器, 用来包装对数据库访问的实例
 * @author DougLei
 */
public interface SessionWrapper {

	/**
	 * 增删改数据
	 * @param sqlData
	 * @return 影响的数据条数
	 */
	int executeUpdate(SqlData sqlData);

	/**
	 * 查询数据
	 * @param sqlData
	 * @return 结果集
	 */
	List<Map<String, Object>> query(SqlData sqlData);
}
