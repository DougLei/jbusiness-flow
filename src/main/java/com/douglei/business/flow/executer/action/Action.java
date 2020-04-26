package com.douglei.business.flow.executer.action;

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
	protected Object setResult(Object value) {
		if(result != null)
			ParameterContext.addParameter(result, value);
		return value;
	}
	
	/**
	 * 执行
	 * @param executeParameter 执行需要的参数
	 * @return
	 */
	public abstract Object execute(ExecuteParameter executeParameter);
}
