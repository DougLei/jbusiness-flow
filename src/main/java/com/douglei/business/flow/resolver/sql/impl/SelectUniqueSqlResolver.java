package com.douglei.business.flow.resolver.sql.impl;

import com.douglei.business.flow.executer.parameter.DeclaredParameter;
import com.douglei.business.flow.executer.sql.core.SelectSql;
import com.douglei.business.flow.executer.sql.core.SelectUniqueSql;

/**
 * 
 * @author DougLei
 */
public class SelectUniqueSqlResolver extends SelectSqlResolver{

	@Override
	public String getType() {
		return SelectUniqueSql.TYPE;
	}

	@Override
	protected SelectSql newSelectSqlInstance(String name, DeclaredParameter[] parameters) {
		return new SelectUniqueSql(name, parameters);
	}
}
 