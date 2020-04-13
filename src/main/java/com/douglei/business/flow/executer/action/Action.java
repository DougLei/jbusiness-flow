package com.douglei.business.flow.executer.action;

import com.douglei.business.flow.db.Session;
import com.douglei.business.flow.executer.ParameterContext;
import com.douglei.business.flow.executer.parameter.Parameter;

/**
 * 
 * @author DougLei
 */
public abstract class Action {
	protected Parameter result;
	
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
	 * 
	 * @param session
	 * @return
	 */
	public abstract Object execute(Session session);
}
