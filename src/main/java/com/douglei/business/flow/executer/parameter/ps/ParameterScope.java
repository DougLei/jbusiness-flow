package com.douglei.business.flow.executer.parameter.ps;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.douglei.business.flow.executer.DataType;
import com.douglei.business.flow.executer.parameter.ActualParameter;
import com.douglei.business.flow.executer.parameter.DeclaredParameter;
import com.douglei.business.flow.executer.parameter.Parameter;
import com.douglei.business.flow.executer.parameter.ParameterOPException;
import com.douglei.business.flow.executer.parameter.ResultParameter;
import com.douglei.business.flow.executer.parameter.Scope;

/**
 * 
 * @author DougLei
 */
public abstract class ParameterScope {
	protected Map<String, ActualParameter> parameterMap = new HashMap<String, ActualParameter>();
	
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
	public Map<String, ActualParameter> clear(){
		parameterMap.clear();
		return null;
	}
	
	/**
	 * 根据配置的参数(ResultParameter或DeclaredParameter)以及实际值, 添加实参
	 * @param parameter
	 * @param value
	 */
	public void addParameter(Parameter parameter, Object value) {
		addParameter(parameter, value, parameterMap);
	}
	protected void addParameter(Parameter parameter, Object value, Map<String, ActualParameter> pm) {
		ActualParameter oldAP = pm.get(parameter.getName());
		if(parameter instanceof ResultParameter) {
			ResultParameter rp = (ResultParameter) parameter;
			if(oldAP == null) { // 不存在, 则添加
				pm.put(parameter.getName(), rp.toActualParameter(value));
			}else { 
				if(rp.getDataType() != null)
					oldAP.updateDataType(compareValueScope(rp.getDataType(), oldAP.getDataType()));
				oldAP.updateValue(value);
			}
		}else if(parameter instanceof DeclaredParameter) {
			if(oldAP != null)
				throw new ParameterOPException("名为"+parameter.getName()+"的参数已经存在, 不能重复定义");
			pm.put(parameter.getName(), ((DeclaredParameter)parameter).toActualParameter(value));
		}
		throw new IllegalArgumentException("添加实参时, 传入的参数类型("+parameter.getClass().getName()+")错误");
	}
	
	/**
	 * d1和d2进行值范围比较, 返回值范围更大的DataType
	 * @param d1
	 * @param d2
	 * @return
	 */
	private DataType compareValueScope(DataType d1, DataType d2) {
		if(d1 == d2)
			return d1;
		if(d1.noValueScope() || d2.noValueScope())
			throw new ParameterOPException("数据类型" + d1.name() + "和"+d2.name()+", 没有值范围的可比性, 其值不可相互转换");
		if(d1.getValueScope() > d2.getValueScope()) {
			return d1;
		}else {
			return d2;
		}
	}

	/**
	 * 根据指定的参数名, 获取对应实参实例
	 * @param parameterName
	 * @return
	 */
	public ActualParameter getParameter(String parameterName) {
		return parameterMap.get(parameterName);
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
	protected Object getValue(String parameterName, String ognlExpression, Map<String, ActualParameter> pm) {
		ActualParameter p = pm.get(parameterName);
		if(p == null) {
			return null;
		}
		return p.getValue(ognlExpression);
	}
	
	/**
	 * 获取本范围内的值map
	 * @param excludeNames 排除这些名字不获取
	 * @return
	 */
	public Map<String, Object> getValueMap(String... excludeNames) {
		return getValueMap(parameterMap, excludeNames);
	}
	protected Map<String, Object> getValueMap(Map<String, ActualParameter> pm, String[] excludeNames){
		if(pm.isEmpty()) {
			return Collections.emptyMap();
		}
		
		Map<String, Object> valueMap = new HashMap<String, Object>(pm.size() - excludeNames.length);
		for(ActualParameter entry : pm.values()) {
			if(!exists(entry.getName(), excludeNames)) {
				valueMap.put(entry.getName(), entry.getValue(null));
			}
		}
		return valueMap;
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
}
