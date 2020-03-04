package com.douglei.business.flow.resolver.action.impl;

import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.core.action.Action;
import com.douglei.business.flow.resolver.action.ActionResolver;

/**
 * 
 * @author DougLei
 */
public class SqlQueryActionResolver implements ActionResolver {

	@Override
	public String getType() {
		return "sql_query";
	}

	@Override
	public Action parse(JSONObject action) {
		// TODO Auto-generated method stub
		return null;
	}
}
