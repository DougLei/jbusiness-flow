package com.douglei.business.flow.executer.action;

import java.util.Map;

import com.douglei.business.flow.executer.parameter.Parameter;

/**
 * 
 * @author DougLei
 */
public abstract class Action {
	protected Parameter result;
	
	/**
	 * 
	 * @param localParameterMap 本地
	 * @return
	 */
	public abstract Object execute(Map<String, Parameter> localParameterMap);
}
