package com.douglei.business.flow.executer.parameter.ps.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import com.douglei.business.flow.executer.parameter.Parameter;
import com.douglei.business.flow.executer.parameter.ps.ParameterScope;

/**
 * 
 * @author DougLei
 */
public class LocalParameterScope extends ParameterScope {
	private boolean stackActivated;
	private Stack<Map<String, Parameter>> parameterStack;
	
	@Override
	public void activateStack() {
		if(!stackActivated) {
			stackActivated = true;
			if(parameterStack == null) {
				parameterStack = new Stack<Map<String,Parameter>>();
			}
		}
		parameterStack.push(new HashMap<String, Parameter>());
	}
	
	@Override
	public Map<String, Parameter> clear() {
		if(stackActivated) {
			Map<String, Parameter> pm = parameterStack.pop();
			if(parameterStack.isEmpty()) {
				stackActivated = false;
			}
			return pm;
		}
		return super.clear();
	}
	
	@Override
	public void addParameter(Parameter parameter) {
		if(stackActivated) {
			addParamter(parameter, parameterStack.peek());
			return;
		}
		super.addParameter(parameter);
	}
	
	@Override
	public Object getValue(Parameter parameter) {
		if(stackActivated) {
			return getValue(parameter, parameterStack.peek());
		}
		return super.getValue(parameter);
	}
	
	@Override
	public Parameter getParameter(String parameterName) {
		if(stackActivated) {
			return parameterStack.peek().get(parameterName);
		}
		return super.getParameter(parameterName);
	}

	@Override
	public void updateValue(Parameter parameter, Object newValue) {
		if(stackActivated) {
			updateValue(parameter, newValue, parameterStack.peek());
			return;
		}
		super.updateValue(parameter, newValue);
	}

	@Override
	public Map<String, Object> getValueMap(String... excludeNames) {
		if(stackActivated) {
			return getValueMap(parameterStack.peek(), excludeNames);
		}
		return super.getValueMap(excludeNames);
	}
}
