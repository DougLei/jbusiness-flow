package com.douglei.business.flow.executer.action.impl.func.method;

import com.douglei.business.flow.executer.parameter.DeclaredParameter;
import com.douglei.business.flow.executer.parameter.ResultParameter;
import com.douglei.business.flow.executer.parameter.Scope;
import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public class Receive {
	private String returnName;
	private ResultParameter resultParameter;
	
	public Receive(String returnName, ResultParameter resultParameter) {
		this.returnName = returnName;
		this.resultParameter = resultParameter;
	}

	public String getReturnName() {
		return returnName;
	}

	public DeclaredParameter updateParameter(DeclaredParameter parameter) {
		if(returnName != parameterName) {
			parameter.updateName(parameterName);
		}
		parameter.updateScope(parameterScope);
		return parameter;
	}
}
