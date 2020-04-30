package com.douglei.business.flow.resolver.sql.impl;

import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.executer.parameter.DeclaredParameter;
import com.douglei.business.flow.executer.sql.Sql;
import com.douglei.business.flow.executer.sql.component.select.condition.ConditionType;
import com.douglei.business.flow.executer.sql.core.DeleteSql;
import com.douglei.business.flow.resolver.ReferenceResolver;
import com.douglei.business.flow.resolver.sql.SqlResolver;

/**
 * 
 * @author DougLei
 */
public class DeleteSqlResolver extends SqlResolver{

	@Override
	public String getType() {
		return DeleteSql.TYPE;
	}

	@Override
	public Sql parse(String name, DeclaredParameter[] parameters, JSONObject content, ReferenceResolver referenceResolver) {
		DeleteSql sql = new DeleteSql(name, parameters);
		sql.setTable(parseTable(content.getJSONObject("table"), referenceResolver));
		sql.setWhereGroups(parseConditionGroupWrapper(ConditionType.WHERE, content, referenceResolver));
		return sql;
	}
}
