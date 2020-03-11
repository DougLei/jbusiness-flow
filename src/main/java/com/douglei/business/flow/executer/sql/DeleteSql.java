package com.douglei.business.flow.executer.sql;

import com.douglei.business.flow.executer.Parameter;

/**
 * 
 * @author DougLei
 */
public class DeleteSql extends Sql {
	public static final byte TYPE = 2; // sql类型: delete

	public DeleteSql(String name, String description, Parameter[] parameters) {
		super(name, description, parameters);
	}

}
