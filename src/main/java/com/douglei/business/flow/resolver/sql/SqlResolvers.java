package com.douglei.business.flow.resolver.sql;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.executer.parameter.DeclaredParameter;
import com.douglei.business.flow.executer.parameter.Scope;
import com.douglei.business.flow.executer.sql.Sql;
import com.douglei.business.flow.resolver.ParameterResolver;
import com.douglei.business.flow.resolver.action.impl.sql.op.SqlDefinedParameterContext;
import com.douglei.tools.instances.scanner.ClassScanner;
import com.douglei.tools.utils.reflect.ClassLoadUtil;
import com.douglei.tools.utils.reflect.ConstructorUtil;
import com.douglei.tools.utils.reflect.ValidationUtil;

/**
 * 
 * @author DougLei
 */
public class SqlResolvers {
	private static final Logger logger = LoggerFactory.getLogger(SqlResolvers.class);
	private static final Map<String, SqlResolver> MAP = new HashMap<String, SqlResolver>(8);
	static {
		ClassScanner cs = new ClassScanner();
		List<String> classPaths = cs.scan(SqlResolvers.class.getPackage().getName() + ".impl");
		
		Class<?> clz;
		SqlResolver sqlResolver;
		for (String cp : classPaths) {
			clz = ClassLoadUtil.loadClass(cp);
			if(Modifier.isAbstract(clz.getModifiers()) || !ValidationUtil.isExtendClass(clz, SqlResolver.class)) {
				continue;
			}
			sqlResolver = (SqlResolver) ConstructorUtil.newInstance(clz);
			MAP.put(sqlResolver.getType().toUpperCase(), sqlResolver);
		}
		cs.destroy();
	}
	
	/**
	 * 解析sql
	 * @param name
	 * @param sqlJSON
	 * @return
	 */
	public static Sql parse(String name, JSONObject sqlJSON) {
		DeclaredParameter[] parameters = SqlDefinedParameterContext.set(ParameterResolver.parseDeclaredParameters(sqlJSON.getJSONArray("params"), Scope.LOCAL));
		SqlResolver resolver = MAP.get(sqlJSON.getString("type").toUpperCase());
		if(logger.isDebugEnabled())
			logger.debug("使用[{}]解析器解析sql", resolver.getClass().getName());
		return resolver.parse(name, parameters, sqlJSON.getJSONObject("content"));
	}
}
