package com.douglei.business.flow.executer.sql;

import com.douglei.business.flow.executer.parameter.Parameter;

/**
 * 
 * @author DougLei
 */
public abstract class Sql {
	protected String name;
	protected String description;
	protected Parameter[] parameters;
	
	protected Sql(String name, String description, Parameter[] parameters) {
		this.name = name;
		this.description = description;
		this.parameters = parameters;
	}
}
