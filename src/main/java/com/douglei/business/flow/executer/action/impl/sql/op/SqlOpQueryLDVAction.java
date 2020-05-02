package com.douglei.business.flow.executer.action.impl.sql.op;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.action.ExecuteParameter;
import com.douglei.business.flow.executer.parameter.DeclaredParameter;
import com.douglei.business.flow.executer.parameter.InvokerParameter;
import com.douglei.business.flow.executer.parameter.Parameter;
import com.douglei.business.flow.executer.sql.Sql;

/**
 * 查询大数据量sql操作
 * ldv: Large data volume
 * @author DougLei
 */
public class SqlOpQueryLDVAction extends SqlOpAction{
	private static final Logger logger = LoggerFactory.getLogger(SqlOpQueryLDVAction.class);
	private DeclaredParameter alias;
	private Parameter aliasParameter;
	private Action[] actions;
	
	public SqlOpQueryLDVAction(Sql sql, InvokerParameter[] parameters, QueryExecuter queryExecuter, DeclaredParameter alias, Parameter aliasParameter, Action[] actions) {
		super(sql, parameters, queryExecuter, null);
		this.alias = alias;
		this.aliasParameter = aliasParameter;
		this.actions = actions;
	}
	
	@Override
	public Object execute(ExecuteParameter executeParameter) {
		if(logger.isDebugEnabled())
			logger.debug("执行[{}]", getClass().getName());
		
		Object firstResult = execute_(executeParameter);
		if(queryExecuter.isPageQuery()) {
			// TODO 
		}else {
			
		}
		
		
		
		
		
		
		
		return null;
	}
}
