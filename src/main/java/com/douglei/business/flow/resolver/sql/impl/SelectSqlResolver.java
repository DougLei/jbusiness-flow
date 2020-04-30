package com.douglei.business.flow.resolver.sql.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.executer.parameter.DeclaredParameter;
import com.douglei.business.flow.executer.sql.Sql;
import com.douglei.business.flow.executer.sql.component.select.With;
import com.douglei.business.flow.executer.sql.core.SelectSql;
import com.douglei.business.flow.resolver.ReferenceResolver;
import com.douglei.business.flow.resolver.sql.SqlResolver;
import com.douglei.tools.utils.CollectionUtil;

/**
 * 
 * @author DougLei
 */
public class SelectSqlResolver extends SqlResolver{

	@Override
	public String getType() {
		return SelectSql.TYPE;
	}
	
	@Override
	public Sql parse(String name, DeclaredParameter[] parameters, JSONObject content, ReferenceResolver referenceResolver) {
		SelectSql sql = new SelectSql(name, parameters);
		
		JSONArray array = content.getJSONArray("withs");
		if(CollectionUtil.unEmpty(array)) {
			With[] withs = new With[array.size()];
			
			JSONObject json;
			for(int i=0;i<array.size();i++) {
				json = array.getJSONObject(i);
				withs[i] = new With(parseConditionValidator(json, referenceResolver), json.getString("alias"), parseColumns(json.getJSONArray("columns"), referenceResolver), parseSelects(json.getJSONArray("selects"), referenceResolver));
			}
			sql.setWiths(withs);
		}
		
		sql.setSelects(parseSelects(content.getJSONArray("selects"), referenceResolver));
		return sql;
	}
}
 