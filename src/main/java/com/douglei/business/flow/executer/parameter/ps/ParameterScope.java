package com.douglei.business.flow.executer.parameter.ps;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.douglei.business.flow.executer.parameter.Parameter;
import com.douglei.business.flow.executer.parameter.Scope;

/**
 * 
 * @author DougLei
 */
public abstract class ParameterScope {
	protected Map<String, Parameter> parameterMap = new HashMap<String, Parameter>();
	
	/**
	 * 所属的范围
	 * @return
	 */
	protected abstract Scope belongScope();
	
	/**
	 * 激活当前范围的参数堆栈
	 */
	public void activateStack() {}
	
	/**
	 * 清空当前范围的参数
	 * @return
	 */
	public Map<String, Parameter> clear(){
		parameterMap.clear();
		return null;
	}
	
	// 给指定的参数map中添加参数
	protected void addParamter(Parameter parameter, Map<String, Parameter> pm) {
		Parameter oldParameter = pm.get(parameter.getName());
		if(oldParameter == null) {
			pm.put(parameter.getName(), parameter);
		}else if(oldParameter.getDataType() != parameter.getDataType()){
			throw new ParameterDataTypeUnmatchingException(belongScope(), oldParameter, parameter);
		}else {
			oldParameter.updateValue(parameter.getValue(null));
		}
	}
	
	/**
	 * 添加指定的参数
	 * @param parameter
	 */
	public void addParameter(Parameter parameter) {
		addParamter(parameter, parameterMap);
	}
	
	// 从指定的参数map中获取指定name和ognl表达式的参数值
	protected Object getValue(String parameterName, String ognlExpression, Map<String, Parameter> pm) {
		Parameter p = pm.get(parameterName);
		if(p == null) {
			return null;
		}
		return p.getValue(ognlExpression);
	}
	
	/**
	 * 获取指定name和ognl表达式的参数value值
	 * @param parameterName
	 * @param ognlExpression
	 * @return
	 */
	public Object getValue(String parameterName, String ognlExpression) {
		return getValue(parameterName, ognlExpression, parameterMap);
	}
	
	/**
	 * 从当前业务流中获取指定name的参数
	 * @param parameterName
	 * @return
	 */
	public Parameter getParameter(String parameterName) {
		return parameterMap.get(parameterName);
	}
	
	// 更新指定参数map中, 指定参数的值
	protected void updateValue(Parameter parameter, Object newValue, Map<String, Parameter> pm) {
		Parameter p = pm.get(parameter.getName());
		if(p != null) {
			p.updateValue(newValue);
		}
	}
	
	/**
	 * 更新本范围内, 指定参数的值
	 * @param parameter
	 * @param newValue
	 */
	public void updateValue(Parameter parameter, Object newValue) {
		updateValue(parameter, newValue, parameterMap);
	}
	
	// 判断names中是否存在name
	private boolean exists(String name, String[] names) {
		if(names.length > 0) {
			for (String name_ : names) {
				if(name_.equals(name)) {
					return true;
				}
			}
		}
		return false;
	}
	
	// 从指定的参数map中, 获取值map
	protected Map<String, Object> getValueMap(Map<String, Parameter> pm, String[] excludeNames){
		if(pm.isEmpty()) {
			return Collections.emptyMap();
		}
		
		Map<String, Object> valueMap = new HashMap<String, Object>(pm.size() - excludeNames.length);
		for(Parameter entry : pm.values()) {
			if(!exists(entry.getName(), excludeNames)) {
				valueMap.put(entry.getName(), entry.getValue(null));
			}
		}
		return valueMap;
	}
	
	/**
	 * 获取本范围内的值map
	 * @param excludeNames 排除这些名字不获取
	 * @return
	 */
	public Map<String, Object> getValueMap(String... excludeNames) {
		return getValueMap(parameterMap, excludeNames);
	}
}
