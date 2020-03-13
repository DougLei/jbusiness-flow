package com.douglei.business.flow.executer;

import java.util.Map;

/**
 * 
 * @author DougLei
 */
public class ParameterContext {
	/**
	 * 输入参数
	 */
	public static final ThreadLocal<Map<String, Parameter>> INPUT_PARAMETER_MAP = new ThreadLocal<Map<String, Parameter>>();
	/**
	 * 输出参数
	 */
	public static final ThreadLocal<Map<String, Parameter>> OUTPUT_PARAMETER_MAP = new ThreadLocal<Map<String, Parameter>>();
	/**
	 * 全局参数
	 */
	public static final ThreadLocal<Map<String, Parameter>> GLOBAL_PARAMETER_MAP = new ThreadLocal<Map<String, Parameter>>();
	
	
	
	
	
	
}
