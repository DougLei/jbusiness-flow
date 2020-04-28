package com.douglei.business.flow.resolver.sql.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.executer.parameter.DeclaredParameter;
import com.douglei.business.flow.executer.sql.Sql;
import com.douglei.business.flow.executer.sql.component.Value;
import com.douglei.business.flow.executer.sql.component.insert.Column;
import com.douglei.business.flow.executer.sql.core.InsertSql;
import com.douglei.business.flow.resolver.sql.SqlResolver;
import com.douglei.tools.utils.CollectionUtil;

/**
 * 
 * @author DougLei
 */
public class InsertSqlResolver extends SqlResolver{

	@Override
	public String getType() {
		return InsertSql.TYPE;
	}

	@Override
	public Sql parse(String name, DeclaredParameter[] parameters, JSONObject content) {
		InsertSql sql = new InsertSql(name, parameters, content.getByteValue("valuesType"));
		sql.setTable(parseTable(content.getJSONObject("table")));
		sql.setColumns(parseColumns(content.getJSONArray("columns")));
		
		switch(sql.getValuesType()) {
			case InsertSql.VALUES_TYPE_VALUE:
				sql.setValues(parseValues(content.getJSONArray("values")));
				break;
			case InsertSql.VALUES_TYPE_SELECT:
				sql.setValues(parseSelects(content.getJSONArray("values")));
				break;
		}
		return sql;
	}
	
	// 解析column数组
	private Column[] parseColumns(JSONArray array) {
		if(CollectionUtil.isEmpty(array))
			return null;
		
		Column[] columns = new Column[array.size()];
		for(int i=0;i<array.size();i++) {
			columns[i] = new Column(array.getJSONObject(i).getString("column"));
		}
		return columns;
	}

	// 解析Value数组
	private Value[] parseValues(JSONArray array) {
		Value[] values = new Value[array.size()];
		for(int i=0;i<array.size();i++) {
			values[i] = parseValue(array.getJSONObject(i));
		}
		return values;
	}
}
