package com.douglei.business.flow.executer.sql;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.business.flow.executer.action.ExecuteParameter;

/**
 * 
 * @author DougLei
 */
public class SqlData {
	private static final Logger logger = LoggerFactory.getLogger(SqlData.class);
	
	private StringBuilder sql;
	private ExecuteParameter executeParameter;
	public SqlData(ExecuteParameter executeParameter) {
		this.sql = new StringBuilder(100);
		this.executeParameter = executeParameter;
	}
	public SqlData(String s, ExecuteParameter executeParameter) {
		this(executeParameter);
		this.sql.append(s);
	}
	
	public SqlData appendSql(char c) {
		sql.append(c);
		return this;
	}
	public SqlData appendSql(String s) {
		sql.append(s);
		return this;
	}
	public SqlData appendSql(Object o) {
		sql.append(o);
		return this;
	}
	
	private List<Object> parameterValues;
	public void addParameterValue(Object parameterValue) {
		if(parameterValues == null)
			parameterValues = new ArrayList<Object>();
		parameterValues.add(parameterValue);
	}
	
	public String getSql() {
		if(logger.isDebugEnabled())
			logger.debug("获取的sql语句为: {}", sql);
		return sql.toString();
	}
	public List<Object> getParameterValues() {
		if(logger.isDebugEnabled())
			logger.debug("获取的sql语句参数list为: {}", parameterValues);
		return parameterValues;
	}
	public ExecuteParameter getExecuteParameter() {
		return executeParameter;
	}
}
