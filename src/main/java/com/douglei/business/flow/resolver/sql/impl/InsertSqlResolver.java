package com.douglei.business.flow.resolver.sql.impl;

import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.Constants;
import com.douglei.business.flow.executer.Parameter;
import com.douglei.business.flow.executer.sql.Sql;
import com.douglei.business.flow.resolver.sql.SqlResolver;

/**
 * 
 * @author DougLei
 */
public class InsertSqlResolver extends SqlResolver{

	@Override
	public byte getType() {
		return Constants.SQL_INSERT;
	}

	@Override
	public Sql parse(String name, String description, Parameter[] parameters, JSONObject content) {
		// TODO Auto-generated method stub
		return null;
	}
}
