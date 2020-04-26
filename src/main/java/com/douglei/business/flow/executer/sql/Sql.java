package com.douglei.business.flow.executer.sql;

import com.douglei.business.flow.executer.DataType;
import com.douglei.business.flow.executer.ParameterContext;
import com.douglei.business.flow.executer.action.ExecuteParameter;
import com.douglei.business.flow.executer.action.impl.sql.op.QueryExecuter;
import com.douglei.business.flow.executer.method.Method;
import com.douglei.business.flow.executer.parameter.DeclaredParameter;
import com.douglei.business.flow.executer.parameter.InvokerParameter;
import com.douglei.business.flow.executer.parameter.Scope;

/**
 * 
 * @author DougLei
 */
public abstract class Sql extends Method{
	
	protected Sql(String name, DeclaredParameter[] parameters) {
		super(name, parameters);
	}
	
	public Object invoke(InvokerParameter[] parameters, ExecuteParameter executeParameter) {
		Object result = super.invoke(parameters, executeParameter);
		ParameterContext.clear(Scope.LOCAL);
		return result;
	}
	
	/**
	 * 执行结果的数据类型
	 * @param queryExecuter
	 * @return
	 */
	public DataType resultDataType(QueryExecuter queryExecuter) {
		return DataType.INTEGER;
	}
}
