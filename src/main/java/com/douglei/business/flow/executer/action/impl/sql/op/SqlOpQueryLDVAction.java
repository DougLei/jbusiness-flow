package com.douglei.business.flow.executer.action.impl.sql.op;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.business.flow.db.DBPageResult;
import com.douglei.business.flow.executer.ParameterContext;
import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.action.ExecuteParameter;
import com.douglei.business.flow.executer.parameter.DeclaredParameter;
import com.douglei.business.flow.executer.parameter.InvokerParameter;
import com.douglei.business.flow.executer.parameter.Parameter;
import com.douglei.business.flow.executer.sql.Sql;
import com.douglei.business.flow.executer.sql.core.SelectSql;
import com.douglei.business.flow.executer.sql.core.LDVSelectSql;

/**
 * 查询大数据量sql操作
 * ldv: Large data volume
 * @author DougLei
 */
public class SqlOpQueryLDVAction extends SqlOpAction{
	private static final long serialVersionUID = 4055263043670371448L;
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
	@SuppressWarnings("rawtypes")
	public Object execute(ExecuteParameter executeParameter) {
		if(logger.isDebugEnabled())
			logger.debug("执行[{}]", getClass().getName());
		
		executeParameter.updateQueryExecuter(queryExecuter);
		
		LDVSelectSql ldvSelectSql = ((SelectSql) super.sql).toLDVSelectSql(parameters, executeParameter);
		Object result = ldvSelectSql.query();
		
		ParameterContext.addParameter(alias, null);
		if(queryExecuter.isPageQuery()) {
			DBPageResult dbPageResult = (DBPageResult) result;
			do {
				executeList(dbPageResult.getResultDatas(), executeParameter);
				if(dbPageResult.hasNextPage() || dbPageResult.isLastPage()) {
					dbPageResult = (DBPageResult) ldvSelectSql.query();;
					continue;
				}
				break;
			}while(true);
		}else {
			executeList((List)result, executeParameter);
		}
		return null;
	}
	
	// 执行list
	@SuppressWarnings("rawtypes")
	private void executeList(List list, ExecuteParameter executeParameter) {
		if(list != null && !list.isEmpty()) {
			for (Object lv : list) {
				ParameterContext.getParameter(aliasParameter).updateValue(lv);
				for (Action action : actions) {
					action.execute(executeParameter);
				}
			}
		}
	}
}
