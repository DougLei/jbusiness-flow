package com.douglei.business.flow.executer.method;

import com.douglei.business.flow.executer.Parameter;
import com.douglei.business.flow.executer.action.Action;

/**
 * 
 * @author DougLei
 */
public class Method {
	private String name;
	private Parameter[] parameters;
	private  Action[] actions;
	private Return return_;
	
	
	
	public Method(String name, Parameter[] parameters, Action[] actions, Return return_) {
		this.name = name;
		this.parameters = parameters;
		this.actions = actions;
		this.return_ = return_;
	}
}
