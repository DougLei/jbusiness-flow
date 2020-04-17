package com.douglei.business.flow.executer.parameter.ps.impl;

import com.douglei.business.flow.executer.parameter.DeclaredParameter;
import com.douglei.business.flow.executer.parameter.Scope;
import com.douglei.business.flow.executer.parameter.ps.ParameterScope;

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
	protected Scope belongScope() {
		return Scope.INOUT;
	}

	@Override
	public void addParameter(DeclaredParameter parameter) {
		in.addParameter(parameter);
		out.addParameter(parameter);
	}
}
