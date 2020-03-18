package com.douglei.business.flow.executer.parameter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 
 * @author DougLei
 */
public class ParameterContext {
	/**
	 * 输入参数
	 */
	private static final ThreadLocal<Map<String, Parameter>> INPUT_PARAMETER_MAP = new ThreadLocal<Map<String, Parameter>>();
	/**
	 * 输出参数
	 */
	private static final ThreadLocal<Map<String, Parameter>> OUTPUT_PARAMETER_MAP = new ThreadLocal<Map<String, Parameter>>();
	/**
	 * 全局参数
	 */
	private static final ThreadLocal<Map<String, Parameter>> GLOBAL_PARAMETER_MAP = new ThreadLocal<Map<String, Parameter>>();
	/**
	 * 本地参数
	 */
	private static final ThreadLocal<Map<String, Parameter>> LOCAL_PARAMETER_MAP = new ThreadLocal<Map<String, Parameter>>();
	
	/**
	 * 获取输出值map, 同时会置空所有参数
	 */
	public static Map<String, Object> getOutputValueMap() {
		Map<String, Object> outputValueMap;
		
		Map<String, Parameter> outputParameterMap = OUTPUT_PARAMETER_MAP.get();
		if(outputParameterMap == null) {
			outputValueMap = Collections.emptyMap();
		}else {
			outputValueMap = new HashMap<String, Object>(outputParameterMap.size());
			for(Entry<String, Parameter> entry : outputParameterMap.entrySet()) {
				outputValueMap.put(entry.getKey(), entry.getValue().getValue());
			}
		}
		
		clearAll();
		return outputValueMap;
	}
	
	/**
	 * 置空所有参数
	 */
	public static void clearAll() {
		if(INPUT_PARAMETER_MAP.get() != null) 
			INPUT_PARAMETER_MAP.remove();
		if(OUTPUT_PARAMETER_MAP.get() != null) 
			OUTPUT_PARAMETER_MAP.remove();
		if(GLOBAL_PARAMETER_MAP.get() != null) 
			GLOBAL_PARAMETER_MAP.remove();
		if(LOCAL_PARAMETER_MAP.get() != null) 
			LOCAL_PARAMETER_MAP.remove();
	}
	
	/**
	 * 重置本地参数map
	 */
	public static void resetLocalParameterMap() {
		if(LOCAL_PARAMETER_MAP.get() != null) {
			LOCAL_PARAMETER_MAP.remove();
		}
		LOCAL_PARAMETER_MAP.set(new HashMap<String, Parameter>());
	}
	
	/**
	 * 添加参数
	 * @param actualParameter 实参
	 */
	public static void addParameter(Parameter actualParameter) {
		switch(actualParameter.getScope()) {
			case IN:
				addParameter(actualParameter, INPUT_PARAMETER_MAP);
				break;
			case INOUT:
				addParameter(actualParameter, INPUT_PARAMETER_MAP);
			case OUT:
				addParameter(actualParameter, OUTPUT_PARAMETER_MAP);
				break;
			case GLOBAL:
				addParameter(actualParameter, GLOBAL_PARAMETER_MAP);
				break;
			case LOCAL:
				addParameter(actualParameter, LOCAL_PARAMETER_MAP);
				break;
		}
	}
	// 给指定的ThreadLocal中添加参数
	private static void addParameter(Parameter actualParameter, ThreadLocal<Map<String, Parameter>> parameterMapThreadLocal) {
		Map<String, Parameter> parameterMap;
		if((parameterMap = parameterMapThreadLocal.get()) == null) {
			parameterMap = new HashMap<String, Parameter>();
			parameterMapThreadLocal.set(parameterMap);
		}
		parameterMap.put(actualParameter.getName(), actualParameter);
	}
	
	/**
	 * 根据配置的参数, 获取对应的值
	 * @param configParameter
	 * @return 
	 */
	public static Object getValue(Parameter configParameter) {
		switch(configParameter.getScope()) {
			case IN:
			case INOUT:
				return getValue(configParameter, INPUT_PARAMETER_MAP);
			case OUT:
				return getValue(configParameter, OUTPUT_PARAMETER_MAP);
			case GLOBAL:
				return getValue(configParameter, GLOBAL_PARAMETER_MAP);
			case LOCAL:
				return getValue(configParameter, LOCAL_PARAMETER_MAP);
		}
		return null;
	}
	// 获取指定参数的值
	private static Object getValue(Parameter configParameter, ThreadLocal<Map<String, Parameter>> parameterMapThreadLocal) {
		Map<String, Parameter> parameterMap;
		if((parameterMap = parameterMapThreadLocal.get()) == null) {
			return null;
		}
		return parameterMap.get(configParameter.getName()).getValue();
	}
	
	/**
	 * 根据配置的参数, 修改对应的值
	 * @param configParameter
	 * @param newActualValue
	 */
	public static void updateValue(Parameter configParameter, Object newActualValue) {
		Parameter actualParameter = null;
		switch(configParameter.getScope()) {
			case IN:
			case INOUT:
				actualParameter = getActualParameter(configParameter, INPUT_PARAMETER_MAP);
			case OUT:
				actualParameter = getActualParameter(configParameter, OUTPUT_PARAMETER_MAP);
			case GLOBAL:
				actualParameter = getActualParameter(configParameter, GLOBAL_PARAMETER_MAP);
			case LOCAL:
				actualParameter = getActualParameter(configParameter, LOCAL_PARAMETER_MAP);
		}
		
		if(actualParameter != null)
			actualParameter.setValue(newActualValue);
	}
	// 获取指定的参数
	private static Parameter getActualParameter(Parameter configParameter, ThreadLocal<Map<String, Parameter>> parameterMapThreadLocal) {
		Map<String, Parameter> parameterMap;
		if((parameterMap = parameterMapThreadLocal.get()) == null) {
			return null;
		}
		return parameterMap.get(configParameter.getName());
	}
}
