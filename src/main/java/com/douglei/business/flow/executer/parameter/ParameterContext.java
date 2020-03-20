package com.douglei.business.flow.executer.parameter;

import java.util.HashMap;
import java.util.Map;

import com.douglei.business.flow.executer.parameter.ps.impl.GlobalParameterScope;
import com.douglei.business.flow.executer.parameter.ps.impl.InOutParameterScope;
import com.douglei.business.flow.executer.parameter.ps.impl.InParameterScope;
import com.douglei.business.flow.executer.parameter.ps.impl.LocalParameterScope;
import com.douglei.business.flow.executer.parameter.ps.impl.OutParameterScope;
import com.douglei.tools.utils.CollectionUtil;

/**
 * 
 * @author DougLei
 */
public class ParameterContext {
	private static final Map<Scope, ParameterScope> PARAMETER_SCOPE_MAP = new HashMap<Scope, ParameterScope>(8);
	static {
		PARAMETER_SCOPE_MAP.put(Scope.IN, new InParameterScope());
		PARAMETER_SCOPE_MAP.put(Scope.OUT, new OutParameterScope());
		PARAMETER_SCOPE_MAP.put(Scope.INOUT, new InOutParameterScope(PARAMETER_SCOPE_MAP.get(Scope.IN), PARAMETER_SCOPE_MAP.get(Scope.OUT)));
		PARAMETER_SCOPE_MAP.put(Scope.GLOBAL, new GlobalParameterScope());
		PARAMETER_SCOPE_MAP.put(Scope.LOCAL, new LocalParameterScope());
	}
	
	/**
	 * 重置指定范围的参数map
	 * @param scope
	 */
	public static void resetParameterMap(Scope scope) {
		PARAMETER_SCOPE_MAP.get(scope).reset();
	}
	
	/**
	 * 清空指定范围的参数map
	 * @param scope
	 * @return 返回被清空的参数map
	 */
	public static Map<String, Parameter> clear(Scope scope) {
		ParameterScope ps = PARAMETER_SCOPE_MAP.get(scope);
		Map<String, Parameter> pm = ps.getParameterMap();
		ps.clear();
		return pm;
	}
	
	/**
	 * 置空所有参数map
	 */
	public static void clearAll() {
		for(ParameterScope parameterScope: PARAMETER_SCOPE_MAP.values())
			parameterScope.clear();
	}
	
	
	
	/**
	 * 添加参数(实参)
	 * @param parameter
	 */
	public static void addParameter(Parameter parameter) {
		PARAMETER_SCOPE_MAP.get(parameter.getScope()).addParameter(parameter);
	}
	
	/**
	 * 根据配置的参数以及实际值, 添加参数(实参)
	 * @param configParameter
	 * @param actualValue
	 */
	public static void addParameter(Parameter configParameter, Object actualValue) {
		PARAMETER_SCOPE_MAP.get(configParameter.getScope()).addParameter(Parameter.getActualParameter(configParameter, actualValue));
	}
	
	/**
	 * 获取指定范围的值map
	 * @param scope
	 * @return
	 */
	public static Map<String, Object> getValueMap(Scope scope) {
		return PARAMETER_SCOPE_MAP.get(scope).getValueMap();
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
	public static Object[] getValues(Parameter[] parameters) {
		if(parameters.length == 0) {
			return CollectionUtil.emptyObjectArray();
		}
		Object[] values = new Object[parameters.length];
		for(byte i=0;i<parameters.length;i++) {
			values[i] = getValue(parameters[i]);
		}
		return values;
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
