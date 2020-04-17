package com.douglei.business.flow.executer.action.impl.sql.op;

import com.douglei.business.flow.db.DBSession;
import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.parameter.DeclaredParameter;
import com.douglei.business.flow.executer.parameter.Parameter;
import com.douglei.business.flow.executer.parameter.ResultParameter;
import com.douglei.business.flow.executer.sql.Sql;

/**
 * 
 * @author DougLei
 */
public class SqlOpAction extends Action {
	private Sql sql;
	private Parameter[] parameters;
	
	public SqlOpAction(Sql sql, Parameter[] parameters, ResultParameter result) {
		this.sql = sql;
		this.parameters = parameters;
		super.result = result;
	}

	@Override
	public Object execute(DBSession session) {
		setResult(sql.invoke(parameters, session));
		return null;
	}
}
