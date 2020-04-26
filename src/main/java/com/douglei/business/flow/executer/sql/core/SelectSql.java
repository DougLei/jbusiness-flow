package com.douglei.business.flow.executer.sql.core;

import com.douglei.business.flow.executer.DataType;
import com.douglei.business.flow.executer.action.ExecuteParameter;
import com.douglei.business.flow.executer.action.impl.sql.op.QueryExecuter;
import com.douglei.business.flow.executer.parameter.DeclaredParameter;
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

	@Override
	protected Object invokeCore(ExecuteParameter executeParameter) {
		SqlData sqlData = new SqlData();
		appendWiths2SqlData(sqlData);
		Component.appendComponents2SqlData(selects, sqlData);
		return executeParameter.getQueryExecuter().executeQuery(executeParameter.getSession(), sqlData.getSql(), sqlData.getParameterValues());
	}
	
	// 追加with子句
	private void appendWiths2SqlData(SqlData sqlData) {
		if(withs != null) {
			sqlData.appendSql("WITH ");
			Component.appendComponents2SqlData(withs, sqlData);
		}
	}
	
	@Override
	public DataType resultDataType(QueryExecuter queryExecuter) {
		return queryExecuter.resultDataType();
	}
}
