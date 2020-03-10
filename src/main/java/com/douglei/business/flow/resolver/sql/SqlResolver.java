package com.douglei.business.flow.resolver.sql;

import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.executer.Parameter;
import com.douglei.business.flow.executer.sql.Sql;

/**
 * 
 * @author DougLei
 */
public abstract class SqlResolver {
	
	/**
	 * 获取类型
	 * @return
	 */
	public abstract byte getType();

	/**
	 * 解析sql
	 * @param name
	 * @param description
	 * @param parameters
	 * @param content
	 * @return
	 */
	public abstract Sql parse(String name, String description, Parameter[] parameters, JSONObject content);
}
