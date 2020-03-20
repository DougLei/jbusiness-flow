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
		/*
		 * 进入方法的时候, 获取对应参数的值, 而不是参数实例
		 * 是不必修改原参数的范围, 因为一旦修改了范围, 在方法执行结束后, 还需要把范围修改回来, 比较复杂
		 * 
		 * 至于方法的返回值, 直接返回parameter, 因为可以直接修改其范围, 进入到新的范围, 而且后续不需要再修改回原范围
		 */
		Object[] values = method.parameterNotEmpty()?ParameterContext.getValues(parameters):CollectionUtil.emptyObjectArray();
		Map<String, Parameter> returnParameters = method.invoke(values);
		if(CollectionUtil.unEmpty(returnParameters)) { // 开始接收参数
			// 1.receives
			if(receives != null) {
				for (Receive receive : receives) {
					ParameterContext.addParameter(receive.updateParameter(returnParameters.get(receive.getReturnName())));
				}
			}
			
			// 2.receiveAll
			if(receiveAll != null) {
				Map<String, Object> returnValues = receiveAll.excludeValues(returnParameters);
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
