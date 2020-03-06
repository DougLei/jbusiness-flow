package com.douglei.business.flow.executer.action.impl.param.op;

import com.douglei.business.flow.executer.Parameter;
import com.douglei.business.flow.executer.action.Action;

/**
 * 
 * @author DougLei
 */
public class ParamOpDeclareAction extends Action{
	private Parameter parameter;
	private String paramName;
	private byte paramScope;
	
	public ParamOpDeclareAction(Parameter parameter, String paramName, byte paramScope) {
		this.parameter = parameter;
		this.paramName = paramName;
		this.paramScope = paramScope;
	}

	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		return null;
	}
}
