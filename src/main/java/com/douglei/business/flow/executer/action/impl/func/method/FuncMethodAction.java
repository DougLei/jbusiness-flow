package com.douglei.business.flow.executer.action.impl.func.method;

import java.util.Map;

import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.method.Method;
import com.douglei.business.flow.executer.parameter.Parameter;

/**
 * 
 * @author DougLei
 */
public class FuncMethodAction extends Action {
	private Method method;
	private Parameter[] parameters;
	private Receive[] receives;
	private ReceiveAll receiveAll;
	
	public FuncMethodAction(Method method, Parameter[] parameters) {
		this.method = method;
		this.parameters = parameters;
	}

	@Override
	public Object execute(Map<String, Parameter> localParameterMap) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public void setReceives(Receive[] receives) {
		this.receives = receives;
	}
	public void setReceiveAll(ReceiveAll receiveAll) {
		this.receiveAll = receiveAll;
	}
}
