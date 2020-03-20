package com.douglei.business.flow.resolver.sql;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.executer.sql.Sql;
import com.douglei.business.flow.resolver.ParameterResolver;
import com.douglei.tools.instances.scanner.ClassScanner;
import com.douglei.tools.utils.reflect.ClassLoadUtil;
import com.douglei.tools.utils.reflect.ConstructorUtil;
import com.douglei.tools.utils.reflect.ValidationUtil;

/**
 * 
 * @author DougLei
 */
public class SqlResolvers {
	private static final Map<Byte, SqlResolver> MAP = new HashMap<Byte, SqlResolver>(8);
	static {
		ClassScanner cs = new ClassScanner();
		List<String> classPaths = cs.scan(SqlResolvers.class.getPackage().getName() + ".impl");
		
		Class<?> clz;
		SqlResolver sqlResolver;
		for (String cp : classPaths) {
			clz = ClassLoadUtil.loadClass(cp);
			if(Modifier.isAbstract(clz.getModifiers()) || !ValidationUtil.isImplementInterface(clz, SqlResolver.class)) {
				continue;
			}
			sqlResolver= (SqlResolver) ConstructorUtil.newInstance(clz);
			registerSqlResolver(sqlResolver);
		}
		cs.destroy();
	}
	
	/**
	 * 注册新的sql解析器
	 * @param sqlResolver
	 */
	public static void registerSqlResolver(SqlResolver sqlResolver) {
		MAP.put(sqlResolver.getType(), sqlResolver);
	}
	
	/**
	 * 解析sql
	 * @param name
	 * @param sqlJSON
	 * @return
	 */
	public static Sql parse(String name, JSONObject sqlJSON) {
		return MAP.get(sqlJSON.getByte("type")).parse(
				   name, 
				   ParameterResolver.parse(sqlJSON.getJSONArray("params")), 
				   sqlJSON.getJSONObject("content"));
	}
	
	/**
	 * 获取指定类型的sql解析器
	 * @param type
	 * @return
	 */
	public static SqlResolver getSqlResolver(byte type) {
		return MAP.get(type);
	}
}
