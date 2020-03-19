package com.douglei.business.flow.executer.action.impl.func.method;

import java.util.Map;

import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.method.Method;
import com.douglei.business.flow.executer.parameter.Parameter;
import com.douglei.business.flow.executer.parameter.ParameterContext;
import com.douglei.tools.utils.CollectionUtil;

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
	public Object execute() {
		Map<String, Parameter> actualParameters = ParameterContext.getActualParameters(parameters);
		Map<String, Object> returnValues = method.invoke(actualParameters);
		if(CollectionUtil.unEmpty(returnValues)) { // 开始接收参数
			// 1.receives
			if(receives != null) {
				for (Receive receive : receives) {
					ParameterContext.addParameter(receive.getParameter(), returnValues.get(receive.getReturnName()));
				}
			}
			
			// 2.receiveAll
			if(receiveAll != null) {
				receiveAll.excludeValues(returnValues);
				ParameterContext.addParameter(receiveAll.getParameter(), returnValues);
			}
		}
		return null;
	}
	
	
	public void setReceives(Receive[] receives) {
		this.receives = receives;
	}
	public void setReceiveAll(ReceiveAll receiveAll) {
		this.receiveAll = receiveAll;
	}
}
