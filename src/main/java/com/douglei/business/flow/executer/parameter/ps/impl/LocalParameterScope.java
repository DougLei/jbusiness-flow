package com.douglei.business.flow.executer.parameter.ps.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import com.douglei.business.flow.executer.parameter.ActualParameter;
import com.douglei.business.flow.executer.parameter.Parameter;
import com.douglei.business.flow.executer.parameter.Scope;
import com.douglei.business.flow.executer.parameter.ps.ParameterScope;

/**
 * 
 * @author DougLei
 */
public class LocalParameterScope extends ParameterScope {
	private boolean stackActivated;
	private Stack<Map<String, ActualParameter>> parameterStack;
	
	@Override
	protected Scope belongScope() {
		return Scope.LOCAL;
	}
	
	@Override
	public void activateStack() {
		if(!stackActivated) {
			stackActivated = true;
			if(parameterStack == null) {
				parameterStack = new Stack<Map<String,ActualParameter>>();
			}
		}
		parameterStack.push(new HashMap<String, ActualParameter>());
	}
	
	@Override
	public Map<String, ActualParameter> clear() {
		if(stackActivated) {
			Map<String, ActualParameter> pm = parameterStack.pop();
			if(parameterStack.isEmpty())
				stackActivated = false;
			return pm;
		}
		return super.clear();
	}
	
	@Override
	public void addParameter(Parameter parameter, Object value) {
		if(stackActivated) {
			addParameter(parameter, value, parameterStack.peek());
		}else {
			super.addParameter(parameter, value);
		}
	}

	@Override
	public ActualParameter getParameter(String parameterName) {
		if(stackActivated) {
			return parameterStack.peek().get(parameterName);
		}
		return super.getParameter(parameterName);
	}
	
	@Override
	public Object getValue(String parameterName, String ognlExpression) {
		if(stackActivated) {
			return getValue(parameterName, ognlExpression, parameterStack.peek());
		}
		return super.getValue(parameterName, ognlExpression);
	}
	
	@Override
	public Map<String, Object> getValueMap(String... excludeNames) {
		if(stackActivated) {
			return getValueMap(parameterStack.peek(), excludeNames);
		}
		return super.getValueMap(excludeNames);
	}
}
