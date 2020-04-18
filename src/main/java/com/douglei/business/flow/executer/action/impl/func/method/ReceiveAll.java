package com.douglei.business.flow.executer.action.impl.func.method;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.douglei.business.flow.executer.parameter.ActualParameter;
import com.douglei.business.flow.executer.parameter.ResultParameter;

/**
 * 
 * @author DougLei
 */
public class ReceiveAll {
	private String[] excludeNames;
	private ResultParameter resultParameter;
	
	public ReceiveAll(String[] excludeNames, ResultParameter resultParameter) {
		this.excludeNames = excludeNames;
		this.resultParameter = resultParameter;
	}
	
	/**
	 * 排除多余的值
	 * @param returnParameters
	 * @return 
	 */
	public Map<String, Object> excludeValues(Map<String, ActualParameter> returnParameters) {
		if(excludeNames != null) {
			for (String name : excludeNames)
				returnParameters.remove(name);
		}
		if(returnParameters.isEmpty())
			return Collections.emptyMap();
		
		Map<String, Object> values = new HashMap<String, Object>(returnParameters.size());
		for(ActualParameter parameter: returnParameters.values()) {
			values.put(parameter.getName(), parameter.getValue(null));
		}
		return values;
	}

	public ResultParameter getParameter() {
		return resultParameter;
	}
}
