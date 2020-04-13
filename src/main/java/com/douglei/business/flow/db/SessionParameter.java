package com.douglei.business.flow.db;

import com.douglei.business.flow.executer.parameter.Parameter;
import com.douglei.business.flow.executer.parameter.Scope;

/**
 * 
 * @author DougLei
 */
public final class SessionParameter {
	private Parameter parameter;
	
	private SessionParameter() {
		parameter = Parameter.newInstance(SESSION_PARAMETER_NAME, Scope.GLOBAL);
	}
	private static final SessionParameter SP = new SessionParameter();
	
	/**
	 * 获取session参数的name
	 */
	public static final String SESSION_PARAMETER_NAME = "_$Session$_";
	
	/**
	 * 获取Session参数的实例(单例)
	 * @return
	 */
	public static SessionParameter getInstance() {
		return SP;
	}
}
