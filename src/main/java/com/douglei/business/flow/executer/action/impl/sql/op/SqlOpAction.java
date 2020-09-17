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
	private static final long serialVersionUID = 1279907042382818408L;
	private static final Logger logger = LoggerFactory.getLogger(SqlOpAction.class);
	protected Sql sql;
	protected InvokerParameter[] parameters;
	protected QueryExecuter queryExecuter;
	
	public SqlOpAction(Sql sql, InvokerParameter[] parameters, QueryExecuter queryExecuter, ResultParameter result) {
		this.sql = sql;
		this.parameters = parameters;
		this.queryExecuter = queryExecuter;
		super.result = result;
	}
	
	@Override
	public Object execute(ExecuteParameter executeParameter) {
		if(logger.isDebugEnabled())
			logger.debug("执行[{}]", getClass().getName());
		
		executeParameter.updateQueryExecuter(queryExecuter);
		setResult(sql.invoke(parameters, executeParameter));
		return null;
	}
}
