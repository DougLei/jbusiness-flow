package com.douglei.business.flow.executer.sql;

import com.douglei.business.flow.executer.method.Method;
import com.douglei.business.flow.executer.parameter.Parameter;

/**
 * 
 * @author DougLei
 */
public abstract class Sql extends Method{
	
	protected Sql(String name, Parameter[] parameters) {
		super.name = name;
		super.parameters = parameters;
	}

	@Override
	protected Object invokeCore() {
		return getSqlExecutor().execute();
	}
	
	/**
	 * 获取sql执行器
	 * @return
	 */
	protected abstract SqlExecutor getSqlExecutor();
}
