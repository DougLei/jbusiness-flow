package com.douglei.business.flow.executer.action.impl.func.method;

import com.douglei.business.flow.executer.Parameter;

/**
 * 
 * @author DougLei
 */
public class Receive {
	private String returnName;
	private Parameter parameter;
	
	public Receive(String returnName, Parameter parameter) {
		this.returnName = returnName;
		this.parameter = parameter;
	}
}
