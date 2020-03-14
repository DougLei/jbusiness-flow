package com.douglei.business.flow.executer;

import java.util.HashMap;
import java.util.Map;

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
	public static final ThreadLocal<Map<String, Parameter>> OUTPUT_PARAMETER_MAP = new ThreadLocal<Map<String, Parameter>>();
	/**
	 * 全局参数
	 */
	public static final ThreadLocal<Map<String, Parameter>> GLOBAL_PARAMETER_MAP = new ThreadLocal<Map<String, Parameter>>();
	
	
	/**
	 * 记录输入参数map
	 * @param inputParameterMap
	 */
	public static void setInputParameterMap(Map<String, Parameter> inputParameterMap) {
		if(INPUT_PARAMETER_MAP.get() != null) {
			throw new RuntimeException("上一轮的输入参数未清空");
		}
		INPUT_PARAMETER_MAP.set(inputParameterMap);
	}

	/**
	 * 初始化输入参数map
	 * @param length
	 */
	static void initialInputParameterMap(byte length) {
		if(INPUT_PARAMETER_MAP.get() != null) {
			INPUT_PARAMETER_MAP.remove();
		}
		INPUT_PARAMETER_MAP.set(new HashMap<String, Parameter>(length));
	}

	/**
	 * 添加输入参数
	 * @param parameter
	 */
	static void addInputParameter(Parameter parameter) {
		INPUT_PARAMETER_MAP.get().put(parameter.getName(), parameter);
	}
	
	
	
	
	
	
}
