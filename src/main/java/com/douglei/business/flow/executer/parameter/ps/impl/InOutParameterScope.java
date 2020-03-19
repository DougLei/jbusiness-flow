package com.douglei.business.flow.executer.parameter.ps.impl;

import com.douglei.business.flow.executer.parameter.Parameter;
import com.douglei.business.flow.executer.parameter.ParameterScope;

/**
 * 
 * @author DougLei
 */
public class InOutParameterScope extends ParameterScope{
	private ParameterScope in;
	private ParameterScope out;
	
	public InOutParameterScope(ParameterScope in, ParameterScope out) {
		this.in = in;
		this.out = out;
	}

	@Override
	public void addParameter(Parameter configParameter, Object actualValue) {
		in.addParameter(configParameter, actualValue);
		out.addParameter(configParameter, actualValue);
	}
}
