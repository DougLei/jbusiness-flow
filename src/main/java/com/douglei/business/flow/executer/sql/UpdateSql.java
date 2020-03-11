package com.douglei.business.flow.executer.sql;

import com.douglei.business.flow.executer.Parameter;

/**
 * 
 * @author DougLei
 */
public class UpdateSql extends Sql {
	public static final byte TYPE = 3; // sql类型: update

	public UpdateSql(String name, String description, Parameter[] parameters) {
		super(name, description, parameters);
	}

}
