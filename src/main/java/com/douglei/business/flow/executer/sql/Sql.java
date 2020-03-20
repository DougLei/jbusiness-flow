package com.douglei.business.flow.executer.sql;

import com.douglei.business.flow.executer.parameter.Parameter;

/**
 * 
 * @author DougLei
 */
public abstract class Sql {
	protected String name;
	protected Parameter[] parameters;
	
	protected Sql(String name, Parameter[] parameters) {
		this.name = name;
		this.parameters = parameters;
	}

	public String getName() {
		return name;
	}
}
