package com.douglei.business.flow.executer.sql;

import com.douglei.business.flow.db.DBSession;
import com.douglei.business.flow.executer.DataType;
import com.douglei.business.flow.executer.ParameterContext;
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
	
	@Override
	public Object invoke(InvokerParameter[] parameters, DBSession session) {
		Object result = super.invoke(parameters, session);
		ParameterContext.clear(Scope.LOCAL);
		return result;
	}
	
	/**
	 * 执行结果的数据类型
	 * @return
	 */
	public DataType resultDataType() {
		return DataType.INTEGER;
	}
}
