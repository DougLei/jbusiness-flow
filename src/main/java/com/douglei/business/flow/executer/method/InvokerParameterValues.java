package com.douglei.business.flow.executer.method;

import java.util.HashMap;
import java.util.Map;

/**
 * 调用者的参数值集合
 * @author DougLei
 */
public class InvokerParameterValues {
	private boolean useTargetName;
	private Object[] values;
	private Map<String, Object> valueMap;
	
	public InvokerParameterValues(int size, boolean useTargetName) {
		if(useTargetName) {
			valueMap = new HashMap<String, Object>(size);
		}else {
			values = new Object[size];
		}
		this.useTargetName = useTargetName;
	}
	
	/**
	 * 添加值
	 * @param value
	 * @param index
	 * @param parameterName
	 */
	public void addValue(Object value, int index, String parameterName) {
		if(useTargetName) {
			valueMap.put(parameterName, value);
		}else {
			values[index] = value;
		}
	}
	
	/**
	 * 获取值
	 * @param index
	 * @param parameterName
	 * @return
	 */
	public Object getValue(int index, String parameterName) {
		if(useTargetName)
			return valueMap.get(parameterName);
		if(index >= values.length)
			return null;
		return values[index];
	}
}
