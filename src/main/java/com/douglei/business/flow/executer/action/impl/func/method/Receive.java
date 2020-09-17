package com.douglei.business.flow.executer.action.impl.func.method;

import java.io.Serializable;

import com.douglei.business.flow.executer.parameter.ResultParameter;

/**
 * 
 * @author DougLei
 */
public class Receive implements Serializable{
	private static final long serialVersionUID = -1103889544434611363L;
	private String returnName;
	private ResultParameter resultParameter;
	
	public Receive(String returnName, ResultParameter resultParameter) {
		if(returnName == null && resultParameter.getName() == null)
			throw new NullPointerException("配置调用方法的action中, receives里的returnName和name, 至少配置一个");
		
		if(returnName == null)
			returnName = resultParameter.getName();
		this.returnName = returnName;
		
		if(resultParameter.getName() == null)
			resultParameter.updateName(returnName);
		this.resultParameter = resultParameter;
	}

	public String getReturnName() {
		return returnName;
	}
	public ResultParameter getResultParameter() {
		return resultParameter;
	}
}
