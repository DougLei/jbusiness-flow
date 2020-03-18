package com.douglei.business.flow.executer.action.impl.sql;

import java.util.Map;

import com.douglei.business.flow.executer.Parameter;
import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.sql.Sql;

/**
 * 
 * @author DougLei
 */
public class SqlAction extends Action {
	private Sql sql;
	private Parameter[] parameters;
	
	public SqlAction(Sql sql, Parameter[] parameters, Parameter result) {
		this.sql = sql;
		this.parameters = parameters;
		this.result = result;
	}

	@Override
	public Object execute(Map<String, Parameter> localParameterMap) {
		// TODO Auto-generated method stub
		return null;
	}
}
