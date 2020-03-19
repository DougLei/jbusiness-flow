package com.douglei.business.flow.executer.method;

import java.util.Map;

import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.parameter.Parameter;

/**
 * 
 * @author DougLei
 */
public class Method {
	private String name;
	private String description;
	private Parameter[] parameters;
	private Action[] actions;
	private Return return_;
	
	public Method(String name, String description, Parameter[] parameters, Action[] actions, Return return_) {
		this.name = name;
		this.description = description;
		this.parameters = parameters;
		this.actions = actions;
		this.return_ = return_;
	}

	public Map<String, Parameter> invoke(Object[] values) {
		// TODO Auto-generated method stub
		return null;
	}
}
