package com.douglei.business.flow.resolver.action.impl.sql.op;

import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.action.impl.sql.op.QueryConfig;
import com.douglei.business.flow.executer.action.impl.sql.op.SqlOpAction;
import com.douglei.business.flow.executer.parameter.InvokerParameter;
import com.douglei.business.flow.executer.sql.Sql;
import com.douglei.business.flow.executer.sql.core.SelectSql;
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
		InvokerParameter[] parameters = ParameterResolver.parseInvokerParameters(content.getJSONArray("params"));
		Sql sql = referenceResolver.parseSql(content.getString("name"));
		QueryConfig queryConfig = (sql instanceof SelectSql)?parseQueryConfig(content.getJSONObject("queryConfig")):null;
		SqlOpAction sqlOpAction = new SqlOpAction(sql, parameters, queryConfig, getResultParameter(actionJSON, sql.resultDataType()));
		return sqlOpAction;
	}
	
	/**
	 * 解析查询配置
	 * @param queryConfigJSON
	 * @return
	 */
	private QueryConfig parseQueryConfig(JSONObject queryConfigJSON) {
		if(queryConfigJSON != null) {
			// TODO 解析出QueryConfig
		}
		return null;
	}
}
