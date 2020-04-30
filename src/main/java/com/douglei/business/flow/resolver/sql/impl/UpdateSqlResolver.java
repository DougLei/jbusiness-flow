package com.douglei.business.flow.resolver.sql.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.executer.parameter.DeclaredParameter;
import com.douglei.business.flow.executer.sql.Sql;
import com.douglei.business.flow.executer.sql.component.select.condition.ConditionType;
import com.douglei.business.flow.executer.sql.component.update.Set;
import com.douglei.business.flow.executer.sql.core.UpdateSql;
import com.douglei.business.flow.resolver.ReferenceResolver;
import com.douglei.business.flow.resolver.sql.SqlResolver;

/**
 * 
 * @author DougLei
 */
public class UpdateSqlResolver extends SqlResolver{

	@Override
	public String getType() {
		return UpdateSql.TYPE;
	}

	@Override
	public Sql parse(String name, DeclaredParameter[] parameters, JSONObject content, ReferenceResolver referenceResolver) {
		UpdateSql sql = new UpdateSql(name, parameters);
		sql.setTable(parseTable(content.getJSONObject("table"), referenceResolver));
		
		JSONArray array = content.getJSONArray("sets");
		Set[] sets = new Set[array.size()];
		JSONObject json;
		for(int i=0;i<array.size();i++) {
			json = array.getJSONObject(i);
			sets[i] = new Set(parseConditionValidator(json, referenceResolver), json.getString("column"), parseValue(json.getJSONObject("value"), referenceResolver));
		}
		sql.setSets(sets);
				
		sql.setWhereGroups(parseConditionGroupWrapper(ConditionType.WHERE, content, referenceResolver));
		return sql;
	}
}
