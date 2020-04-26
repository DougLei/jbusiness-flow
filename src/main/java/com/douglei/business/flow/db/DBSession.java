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
	 * 查询唯一数据
	 * @param sql
	 * @param values
	 * @return
	 */
	Map<String, Object> uniqueQuery(String sql, List<Object> values);
	
	/**
	 * 查询数据
	 * @param sql
	 * @param values
	 * @return 结果集, Map中, key为列名, value为对应的值
	 */
	List<Map<String, Object>> query(String sql, List<Object> values);
	
	/**
	 * 分页查询
	 * @param pageNum 第几页
	 * @param pageSize 一页的数量
	 * @param sql
	 * @param values
	 * @return
	 */
	DBPageResult pageQuery(int pageNum, int pageSize, String sql, List<Object> values); 
	
	/**
	 * 递归查询
	 * @param deep 递归的深度, 小于等于0表示为无限递归
	 * @param pkColumnName 存储主键的列名
	 * @param parentPkColumnName 存储父级主键的列名
	 * @param parentValue 递归语句中, 父主键的值, 可以是单个值, 也可以是数组, 也可以是List, 如果传入null, 则表示查询parentPkColumnName is null的数据
	 * @param childNodeName 父级存储子集的节点名称
	 * @param sql
	 * @param values
	 * @return
	 */
	List<Map<String, Object>> recursiveQuery(int deep, String pkColumnName, String parentPkColumnName, Object parentValue, String childNodeName, String sql, List<Object> values);
	
	/**
	 * 分页递归查询
	 * @param pageNum
	 * @param pageSize
	 * @param deep
	 * @param pkColumnName
	 * @param parentPkColumnName
	 * @param parentValue
	 * @param childNodeName
	 * @param sql
	 * @param values
	 * @return
	 */
	DBPageResult pageRecursiveQuery(int pageNum, int pageSize, int deep, String pkColumnName, String parentPkColumnName, Object parentValue, String childNodeName, String sql, List<Object> values); 
	
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
