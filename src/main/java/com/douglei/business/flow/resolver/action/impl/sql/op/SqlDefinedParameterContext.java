package com.douglei.business.flow.resolver.action.impl.sql.op;

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
	 * @param parameters
	 * @return
	 */
	public static DeclaredParameter[] set(DeclaredParameter[] parameters) {
		if(parameters == null) {
			PARAMETERS.remove();
		}else {
			Map<String, DeclaredParameter> map = new HashMap<String, DeclaredParameter>(parameters.length);
			for (DeclaredParameter p : parameters) {
				map.put(p.getName(), p);
			}
			PARAMETERS.set(map);
		}
		return parameters;
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
