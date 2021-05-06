package com.douglei.business.flow.parser.action.impl.sql.op;

import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.executer.DataType;
import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.action.impl.sql.op.QueryExecuter;
import com.douglei.business.flow.executer.action.impl.sql.op.SqlOpDirectAction;
import com.douglei.business.flow.executer.parameter.InvokerParameter;
import com.douglei.business.flow.executer.parameter.Parameter;
import com.douglei.business.flow.parser.ParameterParser;
import com.douglei.business.flow.parser.ReferenceParser;
import com.douglei.business.flow.parser.action.impl.func.FuncMethodActionParser;

/**
 * 
 * @author DougLei
 */
public class SqlOpDirectActionParser extends FuncMethodActionParser {

	@Override
	public String getType() {
		return "sql_op_direct";
	}
	
	@Override
	public Action parse(JSONObject actionJSON, ReferenceParser referenceResolver) {
		JSONObject content = actionJSON.getJSONObject("content");
		InvokerParameter[] parameters = ParameterParser.parseInvokerParameters(content.getJSONArray("params"));
		
		String type = content.getString("type");
		DataType resultDataType = DataType.INTEGER;
		QueryExecuter queryExecuter = null;
		if("select".equalsIgnoreCase(type)) {
			queryExecuter = parseQueryConfig(content.getJSONObject("queryConfig"));
			resultDataType = queryExecuter.resultDataType();
		}
		
		SqlOpDirectAction action = new SqlOpDirectAction(content.getString("sql"), type, parameters, queryExecuter, getResultParameter(actionJSON, resultDataType));
		return action;
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
