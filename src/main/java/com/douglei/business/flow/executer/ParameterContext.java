package com.douglei.business.flow.executer;

import java.util.HashMap;
import java.util.Map;

import com.douglei.business.flow.executer.parameter.Parameter;
import com.douglei.business.flow.executer.parameter.Scope;
import com.douglei.business.flow.executer.parameter.ps.ParameterScope;
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
	private static final ThreadLocal<Map<Scope, ParameterScope>> PARAMETER_SCOPES = new ThreadLocal<Map<Scope,ParameterScope>>();
	
	// 初始化
	static void initial() {
		Map<Scope, ParameterScope> map = new HashMap<Scope, ParameterScope>(8);
		map.put(Scope.IN, new InParameterScope());
		map.put(Scope.OUT, new OutParameterScope());
		map.put(Scope.INOUT, new InOutParameterScope(map.get(Scope.IN), map.get(Scope.OUT)));
		map.put(Scope.GLOBAL, new GlobalParameterScope());
		map.put(Scope.LOCAL, new LocalParameterScope());
		PARAMETER_SCOPES.set(map);
	}
	
	// 销毁
	static void destory() {
		PARAMETER_SCOPES.remove();
	}
	
	/**
	 * 激活指定范围的参数堆栈
	 * @param scope
	 */
	public static void activateStack(Scope scope) {
		PARAMETER_SCOPES.get().get(scope).activateStack();
	}
	
	/**
	 * 清除指定范围的参数map
	 * @param scope
	 * @return 返回被清除的参数map
	 */
	public static Map<String, Parameter> clear(Scope scope) {
		return PARAMETER_SCOPES.get().get(scope).clear();
	}
	
	/**
	 * 添加参数
	 * @param parameter
	 */
	public static void addParameter(Parameter parameter) {
		PARAMETER_SCOPES.get().get(parameter.getScope()).addParameter(parameter);
	}
	
	/**
	 * 根据配置的参数以及实际值, 添加参数
	 * @param configParameter
	 * @param actualValue
	 */
	public static void addParameter(Parameter configParameter, Object actualValue) {
		PARAMETER_SCOPES.get().get(configParameter.getScope()).addParameter(Parameter.getActualParameter(configParameter, actualValue));
	}
	
	/**
	 * 根据参数, 获取对应的值
	 * @param parameter
	 * @return 
	 */
	public static Object getValue(Parameter parameter) {
		return PARAMETER_SCOPES.get().get(parameter.getScope()).getValue(parameter);
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
	 * 获取输出参数map的值map
	 * @return
	 */
	public static Map<String, Object> getOutValueMap() {
		return ((OutParameterScope) PARAMETER_SCOPES.get().get(Scope.OUT)).getValueMap();
	}
	
	/**
	 * 根据参数, 修改对应的值
	 * @param parameter
	 * @param newValue
	 */
	public static void updateValue(Parameter parameter, Object newValue) {
		PARAMETER_SCOPES.get().get(parameter.getScope()).updateValue(parameter, newValue);
	}
}
