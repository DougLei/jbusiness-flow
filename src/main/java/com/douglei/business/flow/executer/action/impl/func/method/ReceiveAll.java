package com.douglei.business.flow.executer.action.impl.func.method;

import java.util.Map;

import com.douglei.business.flow.executer.parameter.Parameter;

/**
 * 
 * @author DougLei
 */
public class ReceiveAll {
	private String[] excludeNames;
	private Parameter parameter;
	
	public ReceiveAll(String[] excludeNames, Parameter parameter) {
		this.excludeNames = excludeNames;
		this.parameter = parameter;
	}
	
	/**
	 * 排除多余的值
	 * @param values
	 */
	public void excludeValues(Map<String, Object> values) {
		if(excludeNames != null) {
			for (String name : excludeNames) {
				values.remove(name);
			}
		}
	}

	public Parameter getParameter() {
		return parameter;
	}
}
