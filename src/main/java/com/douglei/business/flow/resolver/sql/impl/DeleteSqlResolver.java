package com.douglei.business.flow.resolver.sql.impl;

import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.executer.Parameter;
import com.douglei.business.flow.executer.sql.DeleteSql;
import com.douglei.business.flow.executer.sql.Sql;
import com.douglei.business.flow.resolver.sql.SqlResolver;

/**
 * 
 * @author DougLei
 */
public class DeleteSqlResolver extends SqlResolver{

	@Override
	public byte getType() {
		return DeleteSql.TYPE;
	}

	@Override
	public Sql parse(String name, String description, Parameter[] parameters, JSONObject content) {
		DeleteSql sql = new DeleteSql(name, description, parameters);
		
		
		
		
		return sql;
	}
}
