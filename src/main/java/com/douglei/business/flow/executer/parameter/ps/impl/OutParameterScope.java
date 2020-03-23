package com.douglei.business.flow.executer.parameter.ps.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.douglei.business.flow.executer.parameter.Parameter;
import com.douglei.business.flow.executer.parameter.ps.ParameterScope;

/**
 * 
 * @author DougLei
 */
public class OutParameterScope extends ParameterScope {
	
	/**
	 * 获取本范围内的值map
	 * @return
	 */
	public Map<String, Object> getValueMap() {
		if(parameterMap.isEmpty()) {
			return Collections.emptyMap();
		}
		
		Map<String, Object> valueMap = new HashMap<String, Object>(parameterMap.size());
		for(Entry<String, Parameter> entry : parameterMap.entrySet()) {
			valueMap.put(entry.getKey(), entry.getValue().getValue());
		}
		return valueMap;
	}
}
