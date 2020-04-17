package com.douglei.business.flow.resolver.action.impl.sql;

import java.util.HashMap;
import java.util.Map;

import com.douglei.business.flow.executer.parameter.DeclaredParameter;

/**
 * sql定义的参数容器, 在后续解析sql的时候会用到该参数
 * @author DougLei
 */
public class SqlDefinedParameterContext {
	private static final ThreadLocal<Map<String, DeclaredParameter>> PARAMETERS = new ThreadLocal<Map<String, DeclaredParameter>>();

	/**
	 * 
	 * @param definedParameters
	 * @return
	 */
	public static DeclaredParameter[] set(DeclaredParameter[] definedParameters) {
		Map<String, DeclaredParameter> map = new HashMap<String, DeclaredParameter>(definedParameters.length);
		for (DeclaredParameter dp : definedParameters)
			map.put(dp.getName(), dp);
		PARAMETERS.set(map);
		return definedParameters;
	}
	
	/**
	 * 获取指定name的定义的参数实例
	 * @param name
	 * @return
	 */
	public static DeclaredParameter get(String name) {
		Map<String, DeclaredParameter> map = null;
		if((map = PARAMETERS.get()) != null) {
			DeclaredParameter p= map.get(name);
			if(p != null)
				return p;
		}
		throw new NullPointerException("本sql中, 没有定义name=["+name+"]的参数");
	}
}
