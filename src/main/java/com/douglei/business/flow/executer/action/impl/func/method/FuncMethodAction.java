package com.douglei.business.flow.executer.action.impl.func.method;

import java.util.HashMap;
import java.util.Map;

import com.douglei.business.flow.db.SessionWrapper;
import com.douglei.business.flow.executer.DataType;
import com.douglei.business.flow.executer.ParameterContext;
import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.action.impl.data.op.DataValue;
import com.douglei.business.flow.executer.method.Method;
import com.douglei.business.flow.executer.parameter.Parameter;
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

	@SuppressWarnings("unchecked")
	private Map<String, Parameter> invokeMethod(SessionWrapper session){
		/*
		 * 进入方法的时候, 获取对应参数的值, 而不是参数实例
		 * 是不必修改原参数的范围, 因为一旦修改了范围, 在方法执行结束后, 还需要把范围修改回来, 比较复杂
		 * 
		 * 至于方法的返回值, 直接返回parameter, 因为可以直接修改其范围, 进入到新的范围, 而且后续不需要再修改回原范围
		 */
		return (Map<String, Parameter>)method.invoke(parameters, session);
	}
	
	@Override
	public Object execute(SessionWrapper session) {
		Map<String, Parameter> returnParameters = invokeMethod(session);
		if(CollectionUtil.unEmpty(returnParameters)) { // 开始接收参数
			if(receives != null) {
				for (Receive receive : receives) {
					ParameterContext.addParameter(receive.updateParameter(returnParameters.get(receive.getReturnName())));
				}
			}else if(receiveAll != null) {
				Map<String, Object> returnValues = receiveAll.excludeValues(returnParameters);
				ParameterContext.addParameter(receiveAll.getParameter(), returnValues);
			}
		}
		return null; // 这里就是返回null, 将返回的参数都合并到当前业务流的参数范围中
	}
	
	/**
	 * 返回执行结果, 不会将返回的参数合并到当前业务流的参数范围中
	 * @param session
	 * @param defaultDataValue
	 * @return
	 */
	public DataValue returnExecuteResult(SessionWrapper session, DataValue defaultDataValue) {
		Map<String, Parameter> returnParameters = invokeMethod(session);
		if(CollectionUtil.unEmpty(returnParameters)) {
			Map<String, Object> valueMap = null;
			if(receives != null) {
				Parameter p;
				if(receives.length == 1) {
					p = returnParameters.get(receives[0].getReturnName());
					return new DataValue(p.getValue(null), p.getDataType());
				}else {
					valueMap = new HashMap<String, Object>(receives.length);
					for (Receive receive : receives) {
						p = returnParameters.get(receive.getReturnName());
						valueMap.put(p.getName(), p.getValue(null));
					}
					return new DataValue(valueMap, DataType.OBJECT);
				}
			}else if(receiveAll != null) {
				valueMap = new HashMap<String, Object>(2);
				Map<String, Object> returnValues = receiveAll.excludeValues(returnParameters);
				valueMap.put(receiveAll.getParameter().getName(), returnValues);
				return new DataValue(valueMap, DataType.OBJECT);
			}
		}
		return defaultDataValue;
	}
	
	public void setReceives(Receive[] receives) {
		this.receives = receives;
	}
	public void setReceiveAll(ReceiveAll receiveAll) {
		this.receiveAll = receiveAll;
	}
}
