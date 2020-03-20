package com.douglei.business.flow.resolver.sql.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.executer.parameter.Parameter;
import com.douglei.business.flow.executer.sql.Sql;
import com.douglei.business.flow.executer.sql.UpdateSql;
import com.douglei.business.flow.executer.sql.component.Value;
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
	public Sql parse(String name, Parameter[] parameters, JSONObject content) {
		UpdateSql sql = new UpdateSql(name, parameters);
		sql.setTable(parseTable(content.getJSONObject("table")));
		
		JSONArray array = content.getJSONArray("sets");
		Value[] sets = new Value[array.size()];
		for(int i=0;i<array.size();i++) {
			sets[i] = parseValue(array.getJSONObject(i));
		}
		sql.setSets(sets);
				
		sql.setWhereGroups(parseConditionGroups(content.getJSONArray("whereGroups")));
		return sql;
	}
}
