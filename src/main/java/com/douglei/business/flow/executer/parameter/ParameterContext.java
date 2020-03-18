package com.douglei.business.flow.executer.parameter;

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
	private static final ThreadLocal<Map<String, Parameter>> OUTPUT_PARAMETER_MAP = new ThreadLocal<Map<String, Parameter>>();
	/**
	 * 全局参数
	 */
	private static final ThreadLocal<Map<String, Parameter>> GLOBAL_PARAMETER_MAP = new ThreadLocal<Map<String, Parameter>>();
	
	
	/**
	 * 添加输入参数
	 * @param inputParameter
	 */
	public static void addInputParameter(Parameter inputParameter) {
		Map<String, Parameter> inputParameterMap;
		if((inputParameterMap = INPUT_PARAMETER_MAP.get()) == null) {
			inputParameterMap = new HashMap<String, Parameter>();
			INPUT_PARAMETER_MAP.set(inputParameterMap);
		}
		inputParameterMap.put(inputParameter.getName(), inputParameter);
		
		if(inputParameter.isInputOutParameter()) {
			addOutputParameter(inputParameter);
		}
	}
	
	/**
	 * 添加输出参数
	 * @param outputParameter
	 */
	public static void addOutputParameter(Parameter outputParameter) {
		Map<String, Parameter> outputParameterMap;
		if((outputParameterMap = OUTPUT_PARAMETER_MAP.get()) == null) {
			outputParameterMap = new HashMap<String, Parameter>();
			OUTPUT_PARAMETER_MAP.set(outputParameterMap);
		}
		outputParameterMap.put(outputParameter.getName(), outputParameter);
	}

	
}
