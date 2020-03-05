package com.douglei.business.flow.resolver.action.impl.sql;

import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.executer.Parameter;
import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.action.impl.sql.SqlAction;
import com.douglei.business.flow.resolver.ParameterResolver;
import com.douglei.business.flow.resolver.ReferenceResolver;
import com.douglei.business.flow.resolver.action.ActionResolver;

/**
 * 
 * @author DougLei
 */
public abstract class SqlActionResolver implements ActionResolver {

	@Override
	public Action parse(JSONObject actionJSON, ReferenceResolver referenceResolver) {
		String sql = referenceResolver.parseSql(actionJSON.getString("name"));
		Parameter[] parameters = ParameterResolver.parse(actionJSON.getJSONArray("params"));
		return new SqlAction(sql, parameters);
	}
}
