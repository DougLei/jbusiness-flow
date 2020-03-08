package com.douglei.business.flow.executer.action.impl.func.method;

import com.douglei.business.flow.executer.Parameter;

/**
 * 
 * @author DougLei
 */
public class ReceiveAll {
	private String[] excludeNames;
	private Parameter parameter;
	
	public ReceiveAll(String[] excludeNames, Parameter parameter) {
		this.excludeNames = excludeNames;
		this.parameter = parameter;
	}
}
