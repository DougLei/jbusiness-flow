package com.douglei.business.flow.executer.parameter.ps.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import com.douglei.business.flow.executer.parameter.DeclaredParameter;
import com.douglei.business.flow.executer.parameter.Scope;
import com.douglei.business.flow.executer.parameter.ps.ParameterScope;

/**
 * 
 * @author DougLei
 */
public class LocalParameterScope extends ParameterScope {
	private boolean stackActivated;
	private Stack<Map<String, DeclaredParameter>> parameterStack;
	
	@Override
	protected Scope belongScope() {
		return Scope.LOCAL;
	}
	
	@Override
	public void activateStack() {
		if(!stackActivated) {
			stackActivated = true;
			if(parameterStack == null) {
				parameterStack = new Stack<Map<String,DeclaredParameter>>();
			}
		}
		parameterStack.push(new HashMap<String, DeclaredParameter>());
	}
	
	@Override
	public Map<String, DeclaredParameter> clear() {
		if(stackActivated) {
			Map<String, DeclaredParameter> pm = parameterStack.pop();
			if(parameterStack.isEmpty())
				stackActivated = false;
			return pm;
		}
		return super.clear();
	}
	
	@Override
	public void addParameter(DeclaredParameter parameter) {
		if(stackActivated) {
			addParamter(parameter, parameterStack.peek());
			return;
		}
		super.addParameter(parameter);
	}
	
	@Override
	public Object getValue(String parameterName, String ognlExpression) {
		if(stackActivated) {
			return getValue(parameterName, ognlExpression, parameterStack.peek());
		}
		return super.getValue(parameterName, ognlExpression);
	}
	
	@Override
	public DeclaredParameter getParameter(String parameterName) {
		if(stackActivated) {
			return parameterStack.peek().get(parameterName);
		}
		return super.getParameter(parameterName);
	}

	@Override
	public void updateValue(DeclaredParameter parameter, Object newValue) {
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
