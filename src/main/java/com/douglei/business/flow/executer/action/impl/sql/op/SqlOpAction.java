package com.douglei.business.flow.executer.action.impl.sql.op;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.action.ExecuteParameter;
import com.douglei.business.flow.executer.parameter.InvokerParameter;
import com.douglei.business.flow.executer.parameter.ResultParameter;
import com.douglei.business.flow.executer.sql.Sql;

/**
 * 
 * @author DougLei
 */
public class SqlOpAction extends Action {
	private static final Logger logger = LoggerFactory.getLogger(SqlOpAction.class);
	private Sql sql;
	private InvokerParameter[] parameters;
	private QueryConfig queryConfig;
	
	public SqlOpAction(Sql sql, InvokerParameter[] parameters, QueryConfig queryConfig, ResultParameter result) {
		this.sql = sql;
		this.parameters = parameters;
		this.queryConfig = queryConfig;
		super.result = result;
	}
	
	@Override
	public Object execute(ExecuteParameter executeParameter) {
		if(logger.isDebugEnabled())
			logger.debug("执行[{}]", getClass().getName());
		setResult(sql.invoke(parameters, queryConfig, executeParameter));
		return null;
	}
	
	@Override
	protected Object setResult(Object value) {
		return null;
	}
}
