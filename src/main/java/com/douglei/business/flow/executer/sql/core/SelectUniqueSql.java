package com.douglei.business.flow.executer.sql.core;

import com.douglei.business.flow.db.DBSession;
import com.douglei.business.flow.executer.DataType;
import com.douglei.business.flow.executer.parameter.DeclaredParameter;
import com.douglei.business.flow.executer.sql.SqlData;

/**
 * 
 * @author DougLei
 */
public class SelectUniqueSql extends SelectSql {
	public static final String TYPE = "select_unique";
	
	public SelectUniqueSql(String name, DeclaredParameter[] parameters) {
		super(name, parameters);
	}

	/**
	 * 执行查询
	 * @param sqlData
	 * @param session
	 * @return
	 */
	protected Object executeQuery(SqlData sqlData, DBSession session) {
		return session.uniqueQuery(sqlData.getSql(), sqlData.getParameterValues());
	}

	@Override
	public DataType resultDataType() {
		return DataType.OBJECT;
	}
}
