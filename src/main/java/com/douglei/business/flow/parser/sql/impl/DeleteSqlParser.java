package com.douglei.business.flow.parser.sql.impl;

import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.executer.parameter.DeclaredParameter;
import com.douglei.business.flow.executer.sql.Sql;
import com.douglei.business.flow.executer.sql.component.select.condition.ConditionType;
import com.douglei.business.flow.executer.sql.core.DeleteSql;
import com.douglei.business.flow.parser.ReferenceParser;
import com.douglei.business.flow.parser.sql.SqlParser;

/**
 * 
 * @author DougLei
 */
public class DeleteSqlParser extends SqlParser{

	@Override
	public String getType() {
		return DeleteSql.TYPE;
	}

	@Override
	public Sql parse(String name, DeclaredParameter[] parameters, JSONObject content, ReferenceParser referenceResolver) {
		DeleteSql sql = new DeleteSql(name, parameters);
		sql.setTable(parseTable(content.getJSONObject("table"), referenceResolver));
		sql.setWhereGroups(parseConditionGroupWrapper(ConditionType.WHERE, content, referenceResolver));
		return sql;
	}
}
