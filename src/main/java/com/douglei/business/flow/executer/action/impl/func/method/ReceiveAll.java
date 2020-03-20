package com.douglei.business.flow.executer.action.impl.func.method;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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
	 * @param returnParameters
	 * @return 
	 */
	public Map<String, Object> excludeValues(Map<String, Parameter> returnParameters) {
		if(excludeNames != null) {
			for (String name : excludeNames) {
				returnParameters.remove(name);
			}
		}
		
		Map<String, Object> values = new HashMap<String, Object>(returnParameters.size());
		for(Entry<String, Parameter> entry: returnParameters.entrySet()) {
			values.put(entry.getKey(), entry.getValue().getValue());
		}
		return values;
	}

	public Parameter getParameter() {
		return parameter;
	}
}
