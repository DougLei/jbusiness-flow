package com.douglei.business.flow.executer.sql;

import com.douglei.business.flow.executer.method.Method;
import com.douglei.business.flow.executer.parameter.Parameter;

/**
 * 
 * @author DougLei
 */
public abstract class Sql extends Method{
	protected SqlExecutor sqlExecutor;
	
	protected Sql(String name, Parameter[] parameters) {
		super.name = name;
		super.parameters = parameters;
		this.sqlExecutor = new SqlExecutor();
	}

	@Override
	protected Object invokeCore() {
		appendData2SqlExecutor();
		return sqlExecutor.execute();
	}
	
	/**
	 * 给sql执行器中追加相关的数据
	 */
	protected abstract void appendData2SqlExecutor();
}
