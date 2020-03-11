package com.douglei.business.flow.resolver.sql.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.executer.Parameter;
import com.douglei.business.flow.executer.sql.InsertSql;
import com.douglei.business.flow.executer.sql.Sql;
import com.douglei.business.flow.executer.sql.component.Value;
import com.douglei.business.flow.resolver.sql.SqlResolver;

/**
 * 
 * @author DougLei
 */
public class InsertSqlResolver extends SqlResolver{

	@Override
	public byte getType() {
		return InsertSql.TYPE;
	}

	@Override
	public Sql parse(String name, String description, Parameter[] parameters, JSONObject content) {
		InsertSql sql = new InsertSql(name, description, parameters, content.getByteValue("valuesType"));
		sql.setTable(parseTable(content.getJSONObject("table")));
		
		JSONArray array = content.getJSONArray("columns");
		sql.setColumns(array.toArray(new String[array.size()]));
		
		array = content.getJSONArray("values");
		switch(sql.getValuesType()) {
			case InsertSql.VALUES_TYPE_VALUE:
				short size = (short) array.size();
				Value[] values = new Value[size];
				for(short i=0;i<size;i++) {
					values[i] = parseValue(array.getJSONObject(i));
				}
				sql.setValues(values);
				break;
			case InsertSql.VALUES_TYPE_SELECT:
				sql.setValues(parseSelects(array));
				break;
		}
		return sql;
	}
}
