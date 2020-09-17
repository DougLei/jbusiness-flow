package com.douglei.business.flow.parser.action.impl.sql.op;

import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.executer.DataType;
import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.action.impl.sql.op.QueryExecuter;
import com.douglei.business.flow.executer.action.impl.sql.op.QueryLDVExecuter;
import com.douglei.business.flow.executer.action.impl.sql.op.SqlOpQueryLDVAction;
import com.douglei.business.flow.executer.parameter.DeclaredParameter;
import com.douglei.business.flow.executer.parameter.InvokerParameter;
import com.douglei.business.flow.executer.parameter.Scope;
import com.douglei.business.flow.executer.sql.Sql;
import com.douglei.business.flow.executer.sql.core.SelectSql;
import com.douglei.business.flow.parser.ParameterParser;
import com.douglei.business.flow.parser.ReferenceParser;
import com.douglei.business.flow.parser.action.impl.func.FuncMethodActionParser;

/**
 * 
 * @author DougLei
 */
public class SqlOpQueryLDVActionParser extends FuncMethodActionParser{
	
	@Override
	public String getType() {
		return "sql_op_query_ldv";
	}
	
	@Override
	public Action parse(JSONObject actionJSON, ReferenceParser referenceResolver) {
		JSONObject content = actionJSON.getJSONObject("content");
		Sql sql = referenceResolver.parseSql(content.getString("name"));
		if(!(sql instanceof SelectSql))
			throw new IllegalArgumentException("查询大数据量sql操作action, 只支持查询类型的sql语句");
		InvokerParameter[] parameters = ParameterParser.parseInvokerParameters(content.getJSONArray("params"));
		QueryExecuter queryExecuter = parseQueryConfig(content.getJSONObject("pageNum"), content.getJSONObject("pageSize"));
		
		JSONObject aliasJSON = content.getJSONObject("alias");
		DeclaredParameter alias = ParameterParser.parseDeclaredParameter(aliasJSON, Scope.LOCAL, DataType.OBJECT);
		
		Action[] actions = referenceResolver.parseAction(content.getJSONArray("actions"));
		return new SqlOpQueryLDVAction(sql, parameters, queryExecuter, 
				alias, ParameterParser.parseParameter(aliasJSON), actions);
	}
	
	/**
	 * 解析分页查询配置, 获取查询执行器
	 * @param queryConfigJSON
	 * @return
	 */
	private QueryExecuter parseQueryConfig(JSONObject pageNum, JSONObject pageSize) {
		if(pageNum != null && pageSize != null) {
			return new QueryLDVExecuter(ParameterParser.parseParameter(pageNum), ParameterParser.parseParameter(pageSize));
		}
		return QueryExecuter.DEFAULT_QUERY_EXECUTER;
	}
}
