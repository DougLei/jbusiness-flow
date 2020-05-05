package com.douglei.business.flow.executer.sql.core;

import com.douglei.business.flow.executer.DataType;
import com.douglei.business.flow.executer.ParameterContext;
import com.douglei.business.flow.executer.action.ExecuteParameter;
import com.douglei.business.flow.executer.action.impl.sql.op.QueryExecuter;
import com.douglei.business.flow.executer.parameter.DeclaredParameter;
import com.douglei.business.flow.executer.parameter.InvokerParameter;
import com.douglei.business.flow.executer.parameter.Scope;
import com.douglei.business.flow.executer.sql.Sql;
import com.douglei.business.flow.executer.sql.SqlData;
import com.douglei.business.flow.executer.sql.component.Component;
import com.douglei.business.flow.executer.sql.component.select.Select;
import com.douglei.business.flow.executer.sql.component.select.With;

/**
 * 
 * @author DougLei
 */
public class SelectSql extends Sql {
	public static final String TYPE = "select";
	
	private With[] withs;
	private Select[] selects;
	
	public SelectSql(String name, DeclaredParameter[] parameters) {
		super(name, parameters);
	}

	public void setWiths(With[] withs) {
		this.withs = withs;
	}
	public void setSelects(Select[] selects) {
		this.selects = selects;
	}

	/**
	 * 获取 {@link SqlData} 实例
	 * @param executeParameter
	 * @return
	 */
	private SqlData getSqlData(ExecuteParameter executeParameter) {
		SqlData sqlData = new SqlData(executeParameter);
		appendWiths2SqlData(sqlData);
		Component.appendComponents2SqlData(null, null, selects, sqlData);
		return sqlData;
	}
	
	@Override
	protected Object invokeCore(ExecuteParameter executeParameter) {
		SqlData sqlData = getSqlData(executeParameter);
		return executeParameter.getQueryExecuter().executeQuery(executeParameter.getSession(), sqlData.getSql(), sqlData.getParameterValues());
	}
	
	// 追加with子句
	private void appendWiths2SqlData(SqlData sqlData) {
		if(withs != null) {
			Component.appendComponents2SqlData("WITH ", null, withs, sqlData);
		}
	}
	
	@Override
	public DataType resultDataType(QueryExecuter queryExecuter) {
		return queryExecuter.resultDataType();
	}

	/**
	 * 得到大数量查询sql
	 * @param invokerParameters
	 * @param executeParameter
	 * @return
	 */
	public LDVSelectSql toLDVSelectSql(InvokerParameter[] invokerParameters, ExecuteParameter executeParameter) {
		super.invokePre(invokerParameters);
		SqlData sqlData = getSqlData(executeParameter);
		LDVSelectSql ldvSelectSql = new LDVSelectSql(sqlData, executeParameter);
		ParameterContext.clear(Scope.LOCAL);
		return ldvSelectSql;
	}
}
