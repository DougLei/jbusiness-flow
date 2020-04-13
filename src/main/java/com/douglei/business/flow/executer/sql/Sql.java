package com.douglei.business.flow.executer.sql;

import com.douglei.business.flow.db.SessionWrapper;
import com.douglei.business.flow.executer.ParameterContext;
import com.douglei.business.flow.executer.method.Method;
import com.douglei.business.flow.executer.parameter.Parameter;
import com.douglei.business.flow.executer.parameter.Scope;

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
	public Object invoke(Parameter[] invokerDefinedParameters, SessionWrapper session) {
		Object result = super.invoke(invokerDefinedParameters, session);
		ParameterContext.clear(Scope.LOCAL);
		return result;
	}
}
