package com.douglei.business.flow.executer.action.impl.sql;

import com.douglei.business.flow.executer.Parameter;
import com.douglei.business.flow.executer.action.Action;

/**
 * 
 * @author DougLei
 */
public class SqlAction extends Action {
	private String sql;
	private Parameter[] parameters;
	
	public SqlAction(String sql, Parameter[] parameters) {
		this.sql = sql;
		this.parameters = parameters;
	}

	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		return null;
	}
}
