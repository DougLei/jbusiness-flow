package com.douglei.business.flow.parser.sql;

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
import com.douglei.business.flow.parser.ParameterParser;
import com.douglei.business.flow.parser.ReferenceParser;
import com.douglei.business.flow.parser.action.impl.sql.op.SqlDefinedParameterContext;
import com.douglei.tools.file.scanner.impl.ClassScanner;
import com.douglei.tools.reflect.ClassUtil;

/**
 * 
 * @author DougLei
 */
public class SqlParsers {
	private static final Logger logger = LoggerFactory.getLogger(SqlParsers.class);
	private static final Map<String, SqlParser> MAP = new HashMap<String, SqlParser>(8);
	static {
		List<String> classes = new ClassScanner().scan(SqlParsers.class.getPackage().getName() + ".impl");
		
		Class<?> clz;
		SqlParser sqlResolver;
		for (String clazz : classes) {
			clz = ClassUtil.loadClass1(clazz);
			if(Modifier.isAbstract(clz.getModifiers()) || !ClassUtil.isExtendClass(clz, SqlParser.class)) {
				continue;
			}
			sqlResolver = (SqlParser) ClassUtil.newInstance(clz);
			MAP.put(sqlResolver.getType().toUpperCase(), sqlResolver);
		}
	}
	
	/**
	 * 解析sql
	 * @param name
	 * @param sqlJSON
	 * @param referenceResolver
	 * @return
	 */
	public static Sql parse(String name, JSONObject sqlJSON, ReferenceParser referenceResolver) {
		DeclaredParameter[] parameters = SqlDefinedParameterContext.set(ParameterParser.parseDeclaredParameters(sqlJSON.getJSONArray("params"), Scope.LOCAL, null));
		SqlParser resolver = MAP.get(sqlJSON.getString("type").toUpperCase());
		if(logger.isDebugEnabled())
			logger.debug("使用[{}]解析器解析sql", resolver.getClass().getName());
		return resolver.parse(name, parameters, sqlJSON.getJSONObject("content"), referenceResolver);
	}
}
