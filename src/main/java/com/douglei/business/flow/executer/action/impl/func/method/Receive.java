package com.douglei.business.flow.executer.action.impl.func.method;

import com.douglei.business.flow.executer.parameter.DeclaredParameter;
import com.douglei.business.flow.executer.parameter.Scope;
import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public class Receive {
	private String returnName;
	private String parameterName;
	private Scope parameterScope;
	
	public Receive(String returnName, String parameterName, Scope parameterScope) {
		this.returnName = returnName;
		this.parameterName = StringUtil.isEmpty(parameterName)?returnName:parameterName;
		this.parameterScope = parameterScope;
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
