package com.douglei.business.flow.executer.action.impl.func.method;

import com.douglei.business.flow.executer.parameter.ResultParameter;

/**
 * 
 * @author DougLei
 */
public class Receive {
	private String returnName;
	private ResultParameter resultParameter;
	
	public Receive(String returnName, ResultParameter resultParameter) {
		this.returnName = returnName==null?resultParameter.getName(): returnName;
		this.resultParameter = resultParameter;
	}

	public String getReturnName() {
		return returnName;
	}
	public ResultParameter getResultParameter() {
		return resultParameter;
	}
}
