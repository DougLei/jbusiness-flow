package com.douglei.business.flow.executer.parameter;

import com.douglei.business.flow.executer.DataType;
import com.douglei.tools.instances.ognl.OgnlHandler;
import com.douglei.tools.utils.StringUtil;

/**
 * 参数
 * @author DougLei
 */
public class Parameter implements Cloneable{
	private String name;
	private String ognlExpression; // ognl表达式, 例如name=zhangsan.age, 其中zhangsan为name值, 后面的则是ognl表达式
	private Scope scope;
	private DataType dataType;
	private Object defaultValue;
	private boolean required;
	private boolean stack;
	
	private Object value; // 实际参数的值
	
	public static Parameter newInstance(String name, Scope scope) {
		if(StringUtil.notEmpty(name)) {
			return new Parameter(name, scope);
		}
		return null;
	}
	public static Parameter newInstance(String name, Scope scope, DataType dataType, Object value, boolean required, boolean stack) {
		if(StringUtil.notEmpty(name)) {
			return new Parameter(name, scope, dataType, value, required, stack);
		}
		return null;
	}
	
	// 验证参数的值是否为空
	private static void validateValueIsNull(Parameter parameter, Object value) {
		if(parameter.required && value == null && parameter.defaultValue == null && parameter.dataType.defaultValue() == null) {
			throw new NullPointerException(parameter.scope.getDescription() + "["+parameter.name+"]的值不能为空");
		}
	}
	// 验证参数的值类型是否和配置的匹配
	private static void validateValueDataType(Parameter parameter, Object value) {
		if(value != null && !parameter.dataType.matching(value)) {
			throw new ClassCastException(parameter.scope.getDescription() + "["+parameter.name+"]的值应为"+parameter.dataType.name()+"类型");
		}
	}
	
	/**
	 * 获取OGNL表达式的值
	 * @param parameter
	 * @param value
	 * @return
	 */
	private static Object getOGNLValue(Parameter parameter, Object value) {
		if(value != null && StringUtil.notEmpty(parameter.ognlExpression)) {
			return OgnlHandler.singleInstance().getObjectValue(parameter.ognlExpression, value);
		}
		return value;
	}
	
	/**
	 * 根据配置的参数以及实际值, 获取一个实参实例
	 * @param configParameter 配置的参数
	 * @param actualValue 实际值
	 * @return
	 */
	public static Parameter getActualParameter(Parameter configParameter, Object actualValue) {
		actualValue = getOGNLValue(configParameter, actualValue);
		validateValueIsNull(configParameter, actualValue);
		validateValueDataType(configParameter, actualValue);
		
		Parameter actualParameter;
		try {
			actualParameter = (Parameter) configParameter.clone();
		} catch (CloneNotSupportedException e) {
			// 手动clone
			actualParameter = new Parameter(configParameter.name, configParameter.scope, configParameter.dataType, configParameter.defaultValue, configParameter.required, configParameter.stack);
			actualParameter.ognlExpression = configParameter.ognlExpression;
		}
		
		if(actualValue == null)
			actualValue = actualParameter.defaultValue;
		if(actualValue == null) 
			actualValue = actualParameter.dataType.defaultValue();
		actualParameter.value = actualValue;
		return actualParameter;
	}
	
	private Parameter(String name, Scope scope) {
		short dot = (short) name.indexOf(".");
		if(dot > -1) { // 证明是ognl表达式
			this.name = name.substring(0, dot);
			this.ognlExpression = name.substring(dot+1);
		}else {
			this.name = name;
		}
		this.scope = scope;
	}
	
	private Parameter(String name, Scope scope, DataType dataType, Object defaultValue, boolean required, boolean stack) {
		this(name, scope);
		this.dataType = dataType;
		this.defaultValue = defaultValue;
		this.required = required;
		this.stack = stack;
	}
	
	// 修改实际值
	public void updateValue(Object value) {
		value = getOGNLValue(this, value);
		validateValueDataType(this, value);
		this.value = value;
	}
	// 修改名字
	public void updateName(String name) {
		this.name = name;
	}
	// 修改范围
	public void updateScope(Scope scope) {
		this.scope = scope;
	}
	
	public Object getValue() {
		return value;
	}
	public String getName() {
		return name;
	}
	public Scope getScope() {
		return scope;
	}
	public DataType getDataType() {
		return dataType;
	}
	public Object getDefaultValue() {
		return defaultValue;
	}
	public boolean isRequired() {
		return required;
	}
	public boolean isStack() {
		return stack;
	}
}