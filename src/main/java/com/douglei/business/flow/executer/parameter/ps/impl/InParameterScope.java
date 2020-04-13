package com.douglei.business.flow.executer.parameter.ps.impl;

import com.douglei.business.flow.executer.parameter.Scope;
import com.douglei.business.flow.executer.parameter.ps.ParameterScope;

/**
 * 
 * @author DougLei
 */
public class InParameterScope extends ParameterScope {

	@Override
	protected Scope belongScope() {
		return Scope.IN;
	}
}
