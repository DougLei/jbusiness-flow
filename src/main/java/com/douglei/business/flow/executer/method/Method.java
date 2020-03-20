package com.douglei.business.flow.executer.method;

import java.util.Map;

import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.parameter.Parameter;
import com.douglei.business.flow.executer.parameter.ParameterContext;
import com.douglei.business.flow.executer.parameter.Scope;

/**
 * 
 * @author DougLei
 */
public class Method {
	private String name;
	private Parameter[] parameters;
	private Action[] actions;
	private Return return_;
	
	public Method(String name, Parameter[] parameters, Action[] actions, Return return_) {
		this.name = name;
		this.parameters = parameters;
		this.actions = actions;
		this.return_ = return_;
	}
	
	/**
	 * 调用方法
	 * @param values 实参
	 * @return
	 */
	public Map<String, Parameter> invoke(Object[] values) {
		ParameterContext.resetParameterMap(Scope.METHOD);
		if(parameters.length > 0) {
			for (int i = 0; i < parameters.length; i++) {
				ParameterContext.addParameter(parameters[i], values[i]);
			}
		}
		for (Action action : actions) {
			action.execute();
		}
		
		
		
		
		
		
		return null;
	}

	public String getName() {
		return name;
	}
}
