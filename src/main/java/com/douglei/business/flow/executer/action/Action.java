package com.douglei.business.flow.executer.action;

import com.douglei.business.flow.db.DBSession;
import com.douglei.business.flow.executer.DataType;
import com.douglei.business.flow.executer.ParameterContext;
import com.douglei.business.flow.executer.parameter.ResultParameter;

/**
 * 
 * @author DougLei
 */
public abstract class Action {
	protected ResultParameter result;
	
	/**
	 * 将结果set到参数map中
	 * @param value
	 * @return 传入的value
	 */
//	protected Object setResult(Object value) {
//		return setResult(value, null);
//	}
	
	/**
	 * 将结果set到参数map中
	 * @param value
	 * @param assignDataType 指定的数据类型, 如果传入null, 则使用result的类型
	 * @return 传入的value
	 */
	protected Object setResult(Object value, DataType assignDataType) {
		if(result != null)
			ParameterContext.addParameter(result, value);
		return value;
	}
	
	/**
	 * 
	 * @param session
	 * @return
	 */
	public abstract Object execute(DBSession session);
}
