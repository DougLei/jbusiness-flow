package com.douglei.business.flow.container.reference;

import com.douglei.business.flow.executer.method.Method;
import com.douglei.business.flow.executer.sql.Sql;

/**
 * 存储method和sql的容器
 * @author DougLei
 */
public interface ReferenceContainer {

	/**
	 * 获取指定name的method
	 * @param name
	 * @return
	 */
	Method getMethod(String name);

	/**
	 * 存储method
	 * @param method
	 */
	void putMethod(Method method);

	/**
	 * 获取指定name的sql
	 * @param name
	 * @return
	 */
	Sql getSql(String name);
	
	/**
	 * 存储sql
	 * @param sql
	 */
	void putSql(Sql sql);
}
