package com.douglei.business.flow.resolver.sql.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.executer.parameter.Parameter;
import com.douglei.business.flow.executer.sql.SelectSql;
import com.douglei.business.flow.executer.sql.Sql;
import com.douglei.business.flow.executer.sql.component.select.With;
import com.douglei.business.flow.resolver.sql.SqlResolver;
import com.douglei.tools.utils.CollectionUtil;

/**
 * 
 * @author DougLei
 */
public class SelectSqlResolver extends SqlResolver{

	@Override
	public byte getType() {
		return SelectSql.TYPE;
	}

	@Override
	public Sql parse(String name, Parameter[] parameters, JSONObject content) {
		SelectSql sql = new SelectSql(name, parameters);
		
		JSONArray array = content.getJSONArray("withs");
		byte size = array==null?0:(byte)array.size();
		if(size > 0) {
			With[] withs = new With[size];
			
			JSONObject withJSON;
			for(byte i=0;i<size;i++) {
				withJSON = array.getJSONObject(i);
				withs[i] = new With(withJSON.getString("alias"), parseColumns(withJSON.getJSONArray("columns")), parseSelects(withJSON.getJSONArray("selects")));
			}
			sql.setWiths(withs);
		}
		
		sql.setSelects(parseSelects(content.getJSONArray("selects")));
		return sql;
	}

	private String[] parseColumns(JSONArray array) {
		if(CollectionUtil.unEmpty(array))
			return array.toArray(new String[array.size()]);
		return null;
	}
}
 