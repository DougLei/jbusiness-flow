package com.douglei.business.flow.parser.action.impl.sql.op;

import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.action.impl.sql.op.QueryExecuter;
import com.douglei.business.flow.executer.action.impl.sql.op.SqlOpAction;
import com.douglei.business.flow.executer.parameter.InvokerParameter;
import com.douglei.business.flow.executer.parameter.Parameter;
import com.douglei.business.flow.executer.sql.Sql;
import com.douglei.business.flow.executer.sql.core.SelectSql;
import com.douglei.business.flow.parser.ParameterParser;
import com.douglei.business.flow.parser.ReferenceParser;
import com.douglei.business.flow.parser.action.impl.func.FuncMethodActionParser;

/**
 * 
 * @author DougLei
 */
public class SqlOpActionParser extends FuncMethodActionParser {

	@Override
	public String getType() {
		return "sql_op";
	}
	
	@Override
	public Action parse(JSONObject actionJSON, ReferenceParser referenceResolver) {
		JSONObject content = actionJSON.getJSONObject("content");
		InvokerParameter[] parameters = ParameterParser.parseInvokerParameters(content.getJSONArray("params"));
		Sql sql = referenceResolver.parseSql(content.getString("name"));
		
		QueryExecuter queryExecuter = (sql instanceof SelectSql)?parseQueryConfig(content.getJSONObject("queryConfig")):null;
		SqlOpAction sqlOpAction = new SqlOpAction(sql, parameters, queryExecuter, getResultParameter(actionJSON, sql.resultDataType(queryExecuter)));
		return sqlOpAction;
	}
	
	/**
	 * 解析查询配置, 获取查询执行器
	 * @param queryConfigJSON
	 * @return
	 */
	private QueryExecuter parseQueryConfig(JSONObject queryConfigJSON) {
		if(queryConfigJSON != null) {
			QueryExecuter qe = new QueryExecuter(queryConfigJSON.getByteValue("type"));
			if(qe.isPageQuery())
				qe.setPageQueryParameters(parseParameter(queryConfigJSON, "pageNum"), parseParameter(queryConfigJSON, "pageSize"));
			if(qe.isRecursiveQuery())
				qe.setRecursiveQueryParameters(parseParameter(queryConfigJSON, "deep"), parseParameter(queryConfigJSON, "pkColumnName"), parseParameter(queryConfigJSON, "parentPkColumnName"), parseParameter(queryConfigJSON, "parentValue"), parseParameter(queryConfigJSON, "childNodeName"));
			return qe;
		}
		return QueryExecuter.DEFAULT_QUERY_EXECUTER;
	}
	private Parameter parseParameter(JSONObject queryConfigJSON, String name) {
		return ParameterParser.parseParameter(queryConfigJSON.getJSONObject(name));
	}
}
