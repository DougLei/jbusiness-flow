package com.douglei.business.flow.executer.parameter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.douglei.tools.utils.CollectionUtil;

/**
 * 
 * @author DougLei
 */
public abstract class ParameterScope {
	
	protected ThreadLocal<Map<String, Parameter>> threadLocalParameterMap(){
		return null;
	}
	
	/**
	 * 清空本范围内的参数
	 */
	public final void clear() {
		if(threadLocalParameterMap().get() != null) 
			threadLocalParameterMap().remove();
	}
	
	/**
	 * 重置本范围内的参数
	 */
	public final void reset() {
		if(threadLocalParameterMap().get() != null) {
			threadLocalParameterMap().remove();
		}
		threadLocalParameterMap().set(new HashMap<String, Parameter>());
	}
	
	/**
	 * 根据配置的参数以及实际值, 添加参数(实参)
	 * @param configParameter
	 * @param actualValue
	 */
	public void addParameter(Parameter configParameter, Object actualValue) {
		Parameter actualParameter = Parameter.getActualParameter(configParameter, actualValue);
				
		Map<String, Parameter> parameterMap = threadLocalParameterMap().get();
		if(parameterMap == null) {
			parameterMap = new HashMap<String, Parameter>();
			threadLocalParameterMap().set(parameterMap);
		}
		parameterMap.put(actualParameter.getName(), actualParameter);
	}
	
	/**
	 * 获取指定参数的value值
	 * @param parameter
	 * @return
	 */
	public final Object getValue(Parameter parameter) {
		if(threadLocalParameterMap().get() == null) {
			return null;
		}
		Parameter p = threadLocalParameterMap().get().get(parameter.getName());
		if(p == null) {
			return null;
		}
		return p.getValue();
	}
	
	/**
	 * 获取本范围内的值map
	 * @return
	 */
	public final Map<String, Object> getValueMap() {
		Map<String, Object> valueMap = Collections.emptyMap();
		Map<String, Parameter> parameterMap = threadLocalParameterMap().get();
		if(CollectionUtil.unEmpty(parameterMap)) {
			valueMap = new HashMap<String, Object>(parameterMap.size());
			for(Entry<String, Parameter> entry : parameterMap.entrySet()) {
				valueMap.put(entry.getKey(), entry.getValue().getValue());
			}
		}
		return valueMap;
	}
	
	/**
	 * 更新本范围内, 指定参数的值
	 * @param parameter
	 * @param newValue
	 */
	public final void updateValue(Parameter parameter, Object newValue) {
		Map<String, Parameter> parameterMap = threadLocalParameterMap().get();
		if(CollectionUtil.unEmpty(parameterMap)) {
			Parameter p = parameterMap.get(parameter.getName());
			if(p != null) {
				p.updateValue(newValue);
			}
		}
	}
}
