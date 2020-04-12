package com.douglei.business.flow.container.reference.impl;

import java.util.HashMap;
import java.util.Map;

import com.douglei.business.flow.container.reference.ReferenceContainer;
import com.douglei.business.flow.executer.method.Method;
import com.douglei.business.flow.executer.sql.Sql;

/**
 * 
 * @author DougLei
 */
public class ApplicationReferenceContainer implements ReferenceContainer {
	private Map<String, Method> METHOD_MAP = new HashMap<String, Method>();
	private Map<String, Sql> SQL_MAP = new HashMap<String, Sql>();
	
	@Override
	public Method getMethod(String name) {
		return METHOD_MAP.get(name);
	}

	@Override
	public void putMethod(Method method) {
		METHOD_MAP.put(method.getName(), method);
	}

	@Override
	public Sql getSql(String name) {
		return SQL_MAP.get(name);
	}

	@Override
	public void putSql(Sql sql) {
		SQL_MAP.put(sql.getName(), sql);
	}
	
	@Override
	public void destroy() {
		METHOD_MAP = null;
		SQL_MAP = null;
	}
}
