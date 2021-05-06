package com.douglei.business.flow.executer.action.impl.sql.op;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.business.flow.executer.ParameterContext;
import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.action.ExecuteParameter;
import com.douglei.business.flow.executer.method.InvokerParameterValues;
import com.douglei.business.flow.executer.parameter.InvokerParameter;
import com.douglei.business.flow.executer.parameter.ResultParameter;

/**
 * 
 * @author DougLei
 */
public class SqlOpDirectAction extends Action {
	private static final Logger logger = LoggerFactory.getLogger(SqlOpDirectAction.class);
	private String sql;
	private String type;
	private InvokerParameter[] parameters;
	private QueryExecuter queryExecuter;
	
	public SqlOpDirectAction(String sql, String type, InvokerParameter[] parameters, QueryExecuter queryExecuter, ResultParameter result) {
		this.sql = sql;
		this.type = type;
		this.parameters = parameters;
		this.queryExecuter = queryExecuter;
		super.result = result;
	}
	
	@Override
	public Object execute(ExecuteParameter executeParameter) {
		if(logger.isDebugEnabled())
			logger.debug("执行[{}]", getClass().getName());
		
		InvokerParameterValues values = ParameterContext.getValues(parameters);		
		SQL SQL = extractSQL(values);
		if("select".equalsIgnoreCase(type)) {
			setResult(queryExecuter.executeQuery(executeParameter.getSession(), SQL.sql.toString(), SQL.parameterValues));
		}else {
			setResult(executeParameter.getSession().executeUpdate(SQL.sql.toString(), SQL.parameterValues));
		}
		return null;
	}
	
	/**
	 * 
	 * @param values
	 * @return
	 */
	private SQL extractSQL(InvokerParameterValues values) {
		SQL SQL = new SQL(sql);
		
		StringBuilder parameterName = new StringBuilder(20);
		int index=0, flagIndex=-1;
		while(index < sql.length()) {
			char c = sql.charAt(index);
			
			if(c == '$') {
				if(flagIndex == -1) {
					flagIndex= index;
					SQL.append('?');
				}else {
					SQL.addParameterValue(values.getValue(-1, parameterName.toString()));
					flagIndex=-1;
					parameterName.setLength(0);
				}
			}else if(flagIndex == -1) {
				SQL.append(c);
			}else {
				parameterName.append(c);
			}
			index++;
		}
		return SQL;
	}

	/**
	 * 
	 * @author DougLei
	 */
	private class SQL {
		StringBuilder sql;
		List<Object> parameterValues;
		
		public SQL(String sql) {
			this.sql = new StringBuilder(sql.length());
		}
		
		public void append(char c) {
			sql.append(c);
		}
		public void addParameterValue(Object parameterValue) {
			if(parameterValues==null)
				parameterValues=new ArrayList<Object>();
			parameterValues.add(parameterValue);
		}
	}
}
