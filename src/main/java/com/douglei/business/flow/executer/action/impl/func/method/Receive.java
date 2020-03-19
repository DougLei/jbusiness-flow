package com.douglei.business.flow.executer.action.impl.func.method;

import com.douglei.business.flow.executer.parameter.Parameter;

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

	public String getReturnName() {
		return returnName;
	}
	public Parameter getParameter() {
		return parameter;
	}
}
