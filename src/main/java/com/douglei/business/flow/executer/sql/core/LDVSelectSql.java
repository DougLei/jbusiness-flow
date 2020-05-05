package com.douglei.business.flow.executer.sql.core;

import com.douglei.business.flow.executer.action.ExecuteParameter;
import com.douglei.business.flow.executer.sql.SqlData;

/**
 * 大数据量查询sql
 * @author DougLei
 */
public class LDVSelectSql {
	private SqlData sqlData;
	private ExecuteParameter executeParameter;
	
	LDVSelectSql(SqlData sqlData, ExecuteParameter executeParameter) {
		this.sqlData = sqlData;
		this.executeParameter = executeParameter;
	}

	public Object query() {
		return executeParameter.getQueryExecuter().executeQuery(executeParameter.getSession(), sqlData.getSql(), sqlData.getParameterValues());
	}
}
