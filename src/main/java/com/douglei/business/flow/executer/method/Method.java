package com.douglei.business.flow.executer.method;

import java.util.Map;

import com.douglei.business.flow.executer.ParameterContext;
import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.parameter.Parameter;
import com.douglei.business.flow.executer.parameter.Scope;
import com.douglei.tools.utils.CollectionUtil;

/**
 * 
 * @author DougLei
 */
public class Method {
	protected String name;
	protected Parameter[] parameters;
	private Action[] actions;
	private Return return_;
	
	public Method() {}
	public Method(String name, Parameter[] parameters, Action[] actions, Return return_) {
		this.name = name;
		this.parameters = parameters;
		this.actions = actions;
		this.return_ = return_;
	}
	
	/**
	 * 调用前的预处理
	 * @param definedParameters
	 */
	private void preInvoke(Parameter[] definedParameters) {
		ParameterContext.activateStack(Scope.LOCAL);
		if(parameters.length > 0) {
			Object[] values = ParameterContext.getValues(definedParameters);
			for (int i = 0; i < parameters.length; i++) {
				ParameterContext.addParameter(parameters[i], values[i]);
			}
		}
	}
	
	/**
	 * 调用的核心方法
	 * @return
	 */
	protected Object invokeCore() { 
		for (Action action : actions) {
			action.execute();
		}
		
		Map<String, Parameter> parameterMap = ParameterContext.clear(Scope.LOCAL);
		if(CollectionUtil.unEmpty(parameterMap) && return_ != null) {
			return return_.filter(parameterMap);
		}
		return null;
	}
	
	/**
	 * 调用方法
	 * @param definedParameters 实参
	 * @return
	 */
	public final Object invoke(Parameter[] definedParameters) {
		preInvoke(definedParameters);
		return invokeCore();
	}
	
	public String getName() {
		return name;
	}
}
