package com.douglei.business.flow.executer.action.impl.func.method;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.douglei.business.flow.executer.parameter.DeclaredParameter;

/**
 * 
 * @author DougLei
 */
public class ReceiveAll {
	private String[] excludeNames;
	private DeclaredParameter parameter;
	
	public ReceiveAll(String[] excludeNames, DeclaredParameter parameter) {
		this.excludeNames = excludeNames;
		this.parameter = parameter;
	}
	
	/**
	 * 排除多余的值
	 * @param returnParameters
	 * @return 
	 */
	public Map<String, Object> excludeValues(Map<String, DeclaredParameter> returnParameters) {
		if(excludeNames != null) {
			for (String name : excludeNames)
				returnParameters.remove(name);
		}
		if(returnParameters.isEmpty())
			return Collections.emptyMap();
		
		Map<String, Object> values = new HashMap<String, Object>(returnParameters.size());
		for(DeclaredParameter parameter: returnParameters.values()) {
			values.put(parameter.getName(), parameter.getValue(null));
		}
		return values;
	}

	public DeclaredParameter getParameter() {
		return parameter;
	}
}
