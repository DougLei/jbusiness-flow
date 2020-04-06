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
	 * 方法是否有参数
	 * @return
	 */
	public boolean parameterNotEmpty() {
		return parameters.length > 0;
	}
	
	/**
	 * 调用方法
	 * @param values 实参
	 * @return
	 */
	public Map<String, Parameter> invoke(Object[] values) {
		ParameterContext.activateStack(Scope.LOCAL);
		if(parameterNotEmpty()) {
			for (int i = 0; i < parameters.length; i++) {
				ParameterContext.addParameter(parameters[i], values[i]);
			}
		}
		for (Action action : actions) {
			action.execute();
		}
		
		Map<String, Parameter> parameterMap = ParameterContext.clear(Scope.LOCAL);
		if(CollectionUtil.unEmpty(parameterMap) && return_ != null) {
			return return_.filter(parameterMap);
		}
		return null;
	}

	public Object[] getValues(Parameter[] parameters) {
		if(parameterNotEmpty()) {
			return ParameterContext.getValues(parameters);
		}
		return CollectionUtil.emptyObjectArray();
	}
	
	public String getName() {
		return name;
	}
}
