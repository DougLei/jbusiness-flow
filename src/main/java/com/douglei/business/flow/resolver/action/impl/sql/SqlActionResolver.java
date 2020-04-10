package com.douglei.business.flow.resolver.action.impl.sql;

import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.action.impl.sql.SqlAction;
import com.douglei.business.flow.executer.parameter.Parameter;
import com.douglei.business.flow.executer.sql.Sql;
import com.douglei.business.flow.resolver.ParameterResolver;
import com.douglei.business.flow.resolver.ReferenceResolver;
import com.douglei.business.flow.resolver.action.ActionResolver;

/**
 * 
 * @author DougLei
 */
public abstract class SqlActionResolver extends ActionResolver {

	@Override
	public final Action parse(JSONObject actionJSON, ReferenceResolver referenceResolver) {
		JSONObject content = actionJSON.getJSONObject("content");
		Parameter[] parameters = SqlDefinedParameterContext.set(ParameterResolver.parse(content.getJSONArray("params")));
		Sql sql = referenceResolver.parseSql(content.getString("name"));
		return new SqlAction(sql, parameters, getResult(actionJSON));
	}
}
