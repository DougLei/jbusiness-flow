package com.douglei.business.flow.executer.parameter.ps.impl;

import java.util.Map;
import java.util.Stack;

import com.douglei.business.flow.executer.parameter.Parameter;
import com.douglei.business.flow.executer.parameter.ps.ParameterScope;

/**
 * 
 * @author DougLei
 */
public class LocalParameterScope extends ParameterScope {
	private static final ThreadLocal<Map<String, Parameter>> LOCAL_PARAMETER_MAP = new ThreadLocal<Map<String,Parameter>>();
	private static final ThreadLocal<Stack<Map<String, Parameter>>> LOCAL_PARAMETER_STACK = new ThreadLocal<Stack<Map<String, Parameter>>>(); 
	
	@Override
	protected ThreadLocal<Map<String, Parameter>> threadLocalParameterMap() {
		return LOCAL_PARAMETER_MAP;
	}

	@Override
	public void clear() {
		super.clear();
	}

	@Override
	public void addParameter(Parameter parameter) {
		if(parameter.isStack()) {
			
		}else {
			super.addParameter(parameter);
		}
	}

	@Override
	public Map<String, Parameter> getParameterMap() {
		// TODO Auto-generated method stub
		return super.getParameterMap();
	}

	@Override
	public Object getValue(Parameter parameter) {
		// TODO Auto-generated method stub
		return super.getValue(parameter);
	}

	@Override
	public Map<String, Object> getValueMap() {
		// TODO Auto-generated method stub
		return super.getValueMap();
	}

	@Override
	public void updateValue(Parameter parameter, Object newValue) {
		// TODO Auto-generated method stub
		super.updateValue(parameter, newValue);
	}
}
