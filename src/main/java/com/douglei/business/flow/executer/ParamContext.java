package com.douglei.business.flow.executer;

import java.util.Map;

/**
 * 
 * @author DougLei
 */
public class ParamContext {
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
	/**
	 * 本地参数
	 */
	public static final ThreadLocal<Map<String, Parameter>> LOCAL_PARAMETER_MAP = new ThreadLocal<Map<String, Parameter>>();
	
	
	
	
	
	
	/**
	 * 销毁
	 */
	public void destroy() {
		INPUT_PARAMETER_MAP.remove();;
	}
}
