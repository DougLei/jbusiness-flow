package com.douglei.business.flow.executer.action.impl.sql.op;

import java.io.Serializable;
import java.util.List;

import com.douglei.business.flow.db.DBSession;
import com.douglei.business.flow.executer.DataType;
import com.douglei.business.flow.executer.ParameterContext;
import com.douglei.business.flow.executer.parameter.Parameter;

/**
 * 查询执行器
 * @author DougLei
 */
public class QueryExecuter implements Serializable{
	private static final long serialVersionUID = -4265560739115955111L;

	public static final QueryExecuter DEFAULT_QUERY_EXECUTER = new QueryExecuter(ExecuterType.LIST);
	
	protected ExecuterType type;
	
	protected Parameter pageNum;
	protected Parameter pageSize;
	
	private Parameter deep;
	private Parameter pkColumnName;
	private Parameter parentPkColumnName;
	private Parameter parentValue;
	private Parameter childNodeName;
	
	protected QueryExecuter() {}
	private QueryExecuter(ExecuterType type) {
		this.type = type;
	}
	public QueryExecuter(byte type) {
		this(ExecuterType.toValue(type));
	}
	
	/**
	 * 设置分页查询的参数
	 * @param pageNum
	 * @param pageSize
	 */
	public void setPageQueryParameters(Parameter pageNum, Parameter pageSize) {
		this.pageNum = pageNum;
		this.pageSize = pageSize;
	}
	
	/**
	 * 设置递归查询的参数
	 * @param deep
	 * @param pkColumnName
	 * @param parentPkColumnName
	 * @param parentValue
	 * @param childNodeName
	 */
	public void setRecursiveQueryParameters(Parameter deep, Parameter pkColumnName, Parameter parentPkColumnName, Parameter parentValue, Parameter childNodeName) {
		this.deep = deep;
		this.pkColumnName = pkColumnName;
		this.parentPkColumnName = parentPkColumnName;
		this.parentValue = parentValue;
		this.childNodeName = childNodeName;
	}

	/**
	 * 执行查询
	 * @param session
	 * @param sql
	 * @param values
	 * @return
	 */
	public Object executeQuery(DBSession session, String sql, List<Object> values) {
		switch(type) {
			case LIST:
				return session.query(sql, values);
			case UNIQUE:
				return session.uniqueQuery(sql, values);
			case PAGE:
				return session.pageQuery(getIntegerValue(pageNum), getIntegerValue(pageSize), sql, values);
			case RECURSIVE:
				return session.recursiveQuery(getIntegerValue(deep), getStringValue(pkColumnName), getStringValue(parentPkColumnName), ParameterContext.getValue(parentValue), getStringValue(childNodeName), sql, values);
			case PAGE_RECURSIVE:
				return session.pageRecursiveQuery(getIntegerValue(pageNum), getIntegerValue(pageSize), getIntegerValue(deep), getStringValue(pkColumnName), getStringValue(parentPkColumnName), ParameterContext.getValue(parentValue), getStringValue(childNodeName), sql, values);
		}
		return null;
	}
	
	// 获取数字值
	protected int getIntegerValue(Parameter parameter) {
		return (int) ParameterContext.getValue(parameter);
	}
	// 获取字符串值
	protected String getStringValue(Parameter parameter) {
		return ParameterContext.getValue(parameter).toString();
	}
	
	/**
	 * 查询执行器, 执行结果的数据类型
	 * @return
	 */
	public DataType resultDataType() {
		return type.resultDataType;
	}
	
	/**
	 * 是否是分页查询
	 * @return
	 */
	public boolean isPageQuery() {
		return type == ExecuterType.PAGE || type == ExecuterType.PAGE_RECURSIVE;
	}
	
	/**
	 * 是否是递归查询
	 * @return
	 */
	public boolean isRecursiveQuery() {
		return type == ExecuterType.RECURSIVE || type == ExecuterType.PAGE_RECURSIVE;
	}
}

enum ExecuterType{
	LIST(DataType.LIST),
	UNIQUE(DataType.OBJECT),
	PAGE(DataType.OBJECT),
	RECURSIVE(DataType.LIST),
	PAGE_RECURSIVE(DataType.OBJECT);
	
	DataType resultDataType;
	private ExecuterType(DataType resultDataType) {
		this.resultDataType = resultDataType;
	}

	public static ExecuterType toValue(byte value) {
		if(value == 0)
			return LIST;
		if(value == 1)
			return UNIQUE;
		if(value == 2)
			return PAGE;
		if(value == 3)
			return RECURSIVE;
		if(value == 4)
			return PAGE_RECURSIVE;
		return LIST;
	}
}