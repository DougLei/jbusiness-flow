package com.douglei.business.flow.executer.sql;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author DougLei
 */
public class SqlData {
	private StringBuilder sql;
	public SqlData() {
		sql = new StringBuilder(100);
	}
	public SqlData(String s) {
		this();
		sql.append(s);
	}
	
	public void appendSql(char c) {
		sql.append(c);
	}
	public void appendSql(String s) {
		sql.append(s);
	}
	
	private List<Object> parameterValues;
	public void addParameterValue(Object parameterValue) {
		if(parameterValues == null)
			parameterValues = new ArrayList<Object>();
		parameterValues.add(parameterValue);
	}
	
	public String getSql() {
		return sql.toString();
	}
	public List<Object> getParameterValues() {
		return parameterValues;
	}
}
