package com.douglei.business.flow.resolver.action.impl.sql.op;

import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.action.impl.sql.op.SqlOpAction;
import com.douglei.business.flow.executer.parameter.Parameter;
import com.douglei.business.flow.executer.sql.Sql;
import com.douglei.business.flow.resolver.ParameterResolver;
import com.douglei.business.flow.resolver.ReferenceResolver;
import com.douglei.business.flow.resolver.action.ActionResolver;

/**
 * 
 * @author DougLei
 */
public class SqlOpActionResolver extends ActionResolver {

	@Override
	public String getType() {
		return "sql_op";
	}
	
	@Override
	public Action parse(JSONObject actionJSON, ReferenceResolver referenceResolver) {
		JSONObject content = actionJSON.getJSONObject("content");
		Parameter[] parameters = ParameterResolver.parseParameters(content.getJSONArray("params"));
		Sql sql = referenceResolver.parseSql(content.getString("name"));
		return new SqlOpAction(sql, parameters, getResultParameter(actionJSON, sql.resultDataType()));
	}
}
