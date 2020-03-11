package com.douglei.business.flow.resolver.sql.impl;

import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.executer.Parameter;
import com.douglei.business.flow.executer.sql.Sql;
import com.douglei.business.flow.executer.sql.UpdateSql;
import com.douglei.business.flow.resolver.sql.SqlResolver;

/**
 * 
 * @author DougLei
 */
public class UpdateSqlResolver extends SqlResolver{

	@Override
	public byte getType() {
		return UpdateSql.TYPE;
	}

	@Override
	public Sql parse(String name, String description, Parameter[] parameters, JSONObject content) {
		UpdateSql sql = new UpdateSql(name, description, parameters);
		
		
		
		
		return sql;
	}
}
