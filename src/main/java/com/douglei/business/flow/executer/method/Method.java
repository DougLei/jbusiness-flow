package com.douglei.business.flow.executer.method;

import java.util.Map;

import com.douglei.business.flow.db.DBSession;
import com.douglei.business.flow.executer.ParameterContext;
import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.parameter.ActualParameter;
import com.douglei.business.flow.executer.parameter.DeclaredParameter;
import com.douglei.business.flow.executer.parameter.Parameter;
import com.douglei.business.flow.executer.parameter.Scope;
import com.douglei.tools.utils.CollectionUtil;

/**
 * 
 * @author DougLei
 */
public class Method {
	protected String name;
	protected DeclaredParameter[] parameters;
	private Action[] actions;
	private Return return_;
	
	protected Method(String name, DeclaredParameter[] parameters) {
		this.name = name;
		if(parameters.length > 0) {
			// 如果参数的范围不是本地参数, 则修改为本地参数
			for (DeclaredParameter parameter : parameters) {
				if(parameter.getScope() != Scope.LOCAL)
					parameter.updateScope(Scope.LOCAL);
			}
		}
		this.parameters = parameters;
	}
	public Method(String name, DeclaredParameter[] parameters, Action[] actions, Return return_) {
		this(name, parameters);
		this.actions = actions;
		this.return_ = return_;
	}
	
	/**
	 * 调用前的预处理
	 * 主要是对参数的设置, 以及开启本地参数的堆栈
	 * @param parameters
	 */
	private void invokePre(Parameter[] parameters) {
		ParameterContext.activateStack(Scope.LOCAL);
		if(parameters != null) {
			Object[] values = ParameterContext.getValues(parameters);
			for (int i = 0; i < parameters.length; i++)
				ParameterContext.addParameter(parameters[i], values[i]);
		}
	}
	
	/**
	 * 调用的核心方法
	 * @param session
	 * @return
	 */
	protected Object invokeCore(DBSession session) {
		for (Action action : actions)
			action.execute(session);
		
		Map<String, ActualParameter> parameterMap = ParameterContext.clear(Scope.LOCAL);
		if(CollectionUtil.unEmpty(parameterMap) && return_ != null)
			return return_.filter(parameterMap);
		return null;
	}
	
	
	/**
	 * 调用方法
	 * @param invokerDefinedParameters 调用方定义的参数数组, 根据该参数数组, 从当前业务流中获取相应的value数组
	 * @param session
	 * @return
	 */
	public Object invoke(Parameter[] parameters, DBSession session) {
		invokePre(parameters);
		return invokeCore(session);
	}
	
	public String getName() {
		return name;
	}
}
