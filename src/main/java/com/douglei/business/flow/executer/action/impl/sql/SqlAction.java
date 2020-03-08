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
	
	public SqlAction(String sql, Parameter[] parameters, Parameter result) {
		this.sql = sql;
		this.parameters = parameters;
		this.result = result;
	}

	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		return null;
	}
}
