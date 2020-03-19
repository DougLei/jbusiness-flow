package com.douglei.business.flow.executer.parameter.ps.impl;

import java.util.Map;

import com.douglei.business.flow.executer.parameter.Parameter;
import com.douglei.business.flow.executer.parameter.ParameterScope;

/**
 * 
 * @author DougLei
 */
public class OutParameterScope extends ParameterScope {
	private static final ThreadLocal<Map<String, Parameter>> OUT_PARAMETER_MAP = new ThreadLocal<Map<String,Parameter>>();
	
	@Override
	protected ThreadLocal<Map<String, Parameter>> threadLocalParameterMap() {
		return OUT_PARAMETER_MAP;
	}
}
