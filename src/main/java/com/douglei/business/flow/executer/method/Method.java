package com.douglei.business.flow.executer.method;

import java.io.Serializable;
import java.util.Map;

import com.douglei.business.flow.executer.ParameterContext;
import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.action.ExecuteParameter;
import com.douglei.business.flow.executer.parameter.ActualParameter;
import com.douglei.business.flow.executer.parameter.DeclaredParameter;
import com.douglei.business.flow.executer.parameter.InvokerParameter;
import com.douglei.business.flow.executer.parameter.Scope;

/**
 * 
 * @author DougLei
 */
public class Method implements Serializable{
	private static final long serialVersionUID = 5239000366249685463L;
	protected String name;
	protected DeclaredParameter[] parameters;
	private Action[] actions;
	private Return return_;
	
	protected Method(String name, DeclaredParameter[] parameters) {
		this.name = name;
		this.parameters = parameters;
	}
	public Method(String name, DeclaredParameter[] parameters, Action[] actions, Return return_) {
		this(name, parameters);
		this.actions = actions;
		this.return_ = return_;
	}
	
	/**
	 * 调用前的预处理, 主要是对参数的设置, 以及开启本地参数的堆栈
	 * @param invokerParameters
	 */
	protected final void invokePre(InvokerParameter[] invokerParameters) {
		ParameterContext.activateStack(Scope.LOCAL);
		if(this.parameters != null) {
			InvokerParameterValues values = ParameterContext.getValues(invokerParameters);
			for (int i = 0; i < this.parameters.length; i++)
				ParameterContext.addParameter(this.parameters[i], (values==null?null:values.getValue(i, this.parameters[i].getName())));
		}
	}
	
	/**
	 * 调用的核心方法
	 * @param session
	 * @return
	 */
	protected Object invokeCore(ExecuteParameter executeParameter) {
		for (Action action : actions)
			action.execute(executeParameter);
		
		Map<String, ActualParameter> parameterMap = ParameterContext.clear(Scope.LOCAL);
		if((parameterMap!=null && !parameterMap.isEmpty()) && return_ != null)
			return return_.filter(parameterMap);
		return null;
	}
	
	
	/**
	 * 调用方法
	 * @param invokerParameters 调用方定义的参数数组, 根据该参数数组, 从当前业务流中获取相应的value数组
	 * @param executeParameter
	 * @return
	 */
	public Object invoke(InvokerParameter[] invokerParameters, ExecuteParameter executeParameter) {
		invokePre(invokerParameters);
		return invokeCore(executeParameter);
	}
	
	public String getName() {
		return name;
	}
}
