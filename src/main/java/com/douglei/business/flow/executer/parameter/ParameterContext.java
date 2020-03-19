package com.douglei.business.flow.executer.parameter;

import java.util.HashMap;
import java.util.Map;

import com.douglei.business.flow.executer.parameter.ps.impl.GlobalParameterScope;
import com.douglei.business.flow.executer.parameter.ps.impl.InParameterScope;
import com.douglei.business.flow.executer.parameter.ps.impl.LocalParameterScope;
import com.douglei.business.flow.executer.parameter.ps.impl.OutParameterScope;

/**
 * 
 * @author DougLei
 */
public class ParameterContext {
	private static final Map<Scope, ParameterScope> PARAMETER_SCOPE_MAP = new HashMap<Scope, ParameterScope>(8);
	static {
		PARAMETER_SCOPE_MAP.put(Scope.IN, new InParameterScope());
		PARAMETER_SCOPE_MAP.put(Scope.OUT, new OutParameterScope());
		PARAMETER_SCOPE_MAP.put(Scope.GLOBAL, new GlobalParameterScope());
		PARAMETER_SCOPE_MAP.put(Scope.LOCAL, new LocalParameterScope());
	}
	
	/**
	 * 获取输出值map, 同时会置空所有参数
	 */
	public static Map<String, Object> getOutValueMap() {
		Map<String, Object> outValueMap = PARAMETER_SCOPE_MAP.get(Scope.OUT).getValueMap();
		clearAll();
		return outValueMap;
	}
	
	/**
	 * 置空所有参数
	 */
	public static void clearAll() {
		for(ParameterScope parameterScope: PARAMETER_SCOPE_MAP.values())
			parameterScope.clear();
	}
	
	/**
	 * 重置本地参数map
	 */
	public static void resetLocalParameterMap() {
		PARAMETER_SCOPE_MAP.get(Scope.LOCAL).reset();
	}
	
	/**
	 * 根据配置的参数以及实际值, 添加参数(实参)
	 * @param configParameter
	 * @param actualValue
	 */
	public static void addParameter(Parameter configParameter, Object actualValue) {
		PARAMETER_SCOPE_MAP.get(configParameter.getScope()).addParameter(configParameter, actualValue);
	}
	
	/**
	 * 根据参数, 获取对应的值
	 * @param parameter
	 * @return 
	 */
	public static Object getValue(Parameter parameter) {
		return PARAMETER_SCOPE_MAP.get(parameter.getScope()).getValue(parameter);
	}
	
	/**
	 * 根据参数, 获取对应的值数组
	 * @param parameters
	 * @return
	 */
	public static Object[] getActualValues(Parameter[] parameters) {
		// TODO 
		return null;
	}

	/**
	 * 根据参数, 修改对应的值
	 * @param parameter
	 * @param newValue
	 */
	public static void updateValue(Parameter parameter, Object newValue) {
		PARAMETER_SCOPE_MAP.get(parameter.getScope()).updateValue(parameter, newValue);
	}
}
