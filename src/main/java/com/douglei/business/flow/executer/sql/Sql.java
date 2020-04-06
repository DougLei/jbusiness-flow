package com.douglei.business.flow.executer.sql;

import java.util.Map;

import com.douglei.business.flow.executer.method.Method;
import com.douglei.business.flow.executer.parameter.Parameter;

/**
 * 
 * @author DougLei
 */
public abstract class Sql extends Method{
	
	protected Sql(String name, Parameter[] parameters) {
		this.name = name;
		this.parameters = parameters;
	}

	public Map<String, Parameter> execute(Object[] values) {
		// TODO Auto-generated method stub
		return null;
	}
}
