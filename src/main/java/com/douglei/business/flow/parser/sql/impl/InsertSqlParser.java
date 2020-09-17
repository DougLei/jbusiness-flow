package com.douglei.business.flow.parser.sql.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.executer.parameter.DeclaredParameter;
import com.douglei.business.flow.executer.sql.Sql;
import com.douglei.business.flow.executer.sql.component.Value;
import com.douglei.business.flow.executer.sql.core.InsertSql;
import com.douglei.business.flow.parser.ReferenceParser;
import com.douglei.business.flow.parser.sql.SqlParser;

/**
 * 
 * @author DougLei
 */
public class InsertSqlParser extends SqlParser{

	@Override
	public String getType() {
		return InsertSql.TYPE;
	}

	@Override
	public Sql parse(String name, DeclaredParameter[] parameters, JSONObject content, ReferenceParser referenceResolver) {
		InsertSql sql = new InsertSql(name, parameters, content.getByteValue("valuesType"));
		sql.setTable(parseTable(content.getJSONObject("table"), referenceResolver));
		sql.setColumns(parseColumns(content.getJSONArray("columns"), referenceResolver));
		
		switch(sql.getValuesType()) {
			case InsertSql.VALUES_TYPE_VALUE:
				sql.setValues(parseValues(content.getJSONArray("values"), referenceResolver));
				break;
			case InsertSql.VALUES_TYPE_SELECT:
				sql.setValues(parseSelects(content.getJSONArray("values"), referenceResolver));
				break;
		}
		return sql;
	}
	
	// 解析Value数组
	private Value[] parseValues(JSONArray array, ReferenceParser referenceResolver) {
		Value[] values = new Value[array.size()];
		for(int i=0;i<array.size();i++) {
			values[i] = parseValue(array.getJSONObject(i), referenceResolver);
		}
		return values;
	}
}
