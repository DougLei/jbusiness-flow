package com.douglei.business.flow.resolver.sql.impl;

import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.executer.parameter.Parameter;
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
	public Sql parse(String name, Parameter[] parameters, JSONObject content) {
		DeleteSql sql = new DeleteSql(name, parameters);
		sql.setTable(parseTable(content.getJSONObject("table")));
		sql.setWhereGroups(parseConditionGroups(content.getJSONArray("whereGroups")));
		return sql;
	}
}
