package com.douglei.business.flow.executer;

import java.util.HashMap;
import java.util.Map;

import com.douglei.business.flow.executer.method.InvokerParameterValues;
import com.douglei.business.flow.executer.parameter.ActualParameter;
import com.douglei.business.flow.executer.parameter.InvokerParameter;
import com.douglei.business.flow.executer.parameter.Parameter;
import com.douglei.business.flow.executer.parameter.Scope;
import com.douglei.business.flow.executer.parameter.ps.ParameterScope;
import com.douglei.business.flow.executer.parameter.ps.impl.GlobalParameterScope;
import com.douglei.business.flow.executer.parameter.ps.impl.InOutParameterScope;
import com.douglei.business.flow.executer.parameter.ps.impl.InParameterScope;
import com.douglei.business.flow.executer.parameter.ps.impl.LocalParameterScope;
import com.douglei.business.flow.executer.parameter.ps.impl.OutParameterScope;
import com.douglei.tools.utils.StringUtil;

/**
 * 参数上下文
 * @author DougLei
 */
public class ParameterContext {
	private static final ThreadLocal<Map<Scope, ParameterScope>> PARAMETER_SCOPES_CONTEXT = new ThreadLocal<Map<Scope,ParameterScope>>();
	
	// 初始化
	static void initial() {
		Map<Scope, ParameterScope> map = new HashMap<Scope, ParameterScope>(8);
		map.put(Scope.IN, new InParameterScope());
		map.put(Scope.OUT, new OutParameterScope());
		map.put(Scope.INOUT, new InOutParameterScope(map.get(Scope.IN), map.get(Scope.OUT)));
		map.put(Scope.GLOBAL, new GlobalParameterScope());
		map.put(Scope.LOCAL, new LocalParameterScope());
		PARAMETER_SCOPES_CONTEXT.set(map);
	}
	
	// 销毁
	static void destory() {
		PARAMETER_SCOPES_CONTEXT.remove();
	}
	
	/**
	 * 激活指定范围的参数堆栈
	 * @param scope
	 */
	public static void activateStack(Scope scope) {
		PARAMETER_SCOPES_CONTEXT.get().get(scope).activateStack();
	}
	
	/**
	 * 清除指定范围的参数map
	 * @param scope
	 * @return 返回被清除的参数map
	 */
	public static Map<String, ActualParameter> clear(Scope scope) {
		return PARAMETER_SCOPES_CONTEXT.get().get(scope).clear();
	}
	
	/**
	 * 根据配置的参数(ResultParameter或DeclaredParameter)以及实际值, 添加实参
	 * @param parameter
	 * @param value
	 */
	public static void addParameter(Parameter parameter, Object value) {
		PARAMETER_SCOPES_CONTEXT.get().get(parameter.getScope()).addParameter(parameter, value);
	}
	
	
	/**
	 * 根据指定的参数, 获取对应实参实例
	 * @param parameter
	 * @return
	 */
	public static ActualParameter getParameter(Parameter parameter) {
		return PARAMETER_SCOPES_CONTEXT.get().get(parameter.getScope()).getParameter(parameter.getName());
	}
	
	
	/**
	 * 根据指定的参数, 获取对应实参的值
	 * @param parameter
	 * @return
	 */
	public static Object getValue(Parameter parameter) {
		Object value = null;
		if(parameter.getName() != null)
			value = PARAMETER_SCOPES_CONTEXT.get().get(parameter.getScope()).getValue(parameter.getName(), parameter.getOgnlExpression());
		if(value == null)
			return parameter.getDefaultValue();
		return value;
	}
	
	/**
	 * 根据调用者参数, 获取对应的结果值
	 * @param parameters
	 * @return
	 */
	public static InvokerParameterValues getValues(InvokerParameter[] parameters) {
		if(parameters == null) {
			return null;
		}
		
		InvokerParameterValues values = new InvokerParameterValues(parameters.length, StringUtil.notEmpty(parameters[0].getTargetName()));
		for(byte i=0;i<parameters.length;i++) {
			values.addValue(getValue(parameters[i]), i, parameters[i].getTargetName());
		}
		return values;
	}
	
	/**
	 * 获取指定范围的参数map的值map
	 * @param scope
	 * @param excludeNames 排除这些名字不获取
	 * @return
	 */
	public static Map<String, Object> getValueMap(Scope scope, String... excludeNames) {
		return PARAMETER_SCOPES_CONTEXT.get().get(scope).getValueMap(excludeNames);
	}
}
