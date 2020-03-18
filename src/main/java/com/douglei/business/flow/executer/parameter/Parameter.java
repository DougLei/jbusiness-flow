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
	private boolean required = true;
	private Object defaultValue;
	private String description;
	
	private Object value; // 实际参数的值
	
	public static Parameter newInstance(String name, Scope scope) {
		if(StringUtil.notEmpty(name)) {
			return new Parameter(name, scope);
		}
		return null;
	}
	public static Parameter newInstance(String name, Scope scope, DataType dataType, Boolean required, Object value, String description) {
		if(StringUtil.notEmpty(name)) {
			return new Parameter(name, scope, dataType, required==null?true:required.booleanValue(), value, description);
		}
		return null;
	}
	
	/**
	 * 根据配置的参数以及实际值, 获取一个实参实例
	 * @param configParameter 配置的参数
	 * @param actualValue 实际值
	 * @return
	 */
	public static Parameter getActualParameter(Parameter configParameter, Object actualValue) {
		if(actualValue == null && configParameter.required && configParameter.defaultValue == null) {
			throw new NullPointerException(configParameter.scope.getDescription() + "["+configParameter.name+"]的值不能为空");
		}
		if(actualValue != null && !configParameter.dataType.matching(actualValue)) {
			throw new ClassCastException(configParameter.scope.getDescription() + "["+configParameter.name+"]的值应为"+configParameter.dataType.name()+"类型");
		}
		
		Parameter actualParameter;
		try {
			actualParameter = (Parameter) configParameter.clone();
		} catch (CloneNotSupportedException e) {
			actualParameter = new Parameter(configParameter.name, configParameter.scope, configParameter.dataType, configParameter.required, configParameter.defaultValue, configParameter.description);
			actualParameter.ognlExpression = configParameter.ognlExpression;
		}
		actualParameter.setValue(actualValue);
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
	
	private Parameter(String name, Scope scope, DataType dataType, boolean required, Object defaultValue, String description) {
		this(name, scope);
		this.dataType = dataType;
		this.required = required;
		this.defaultValue = defaultValue;
		this.description = description;
	}
	
	/**
	 * 设置实际值
	 * @param actualValue
	 */
	public void setValue(Object value) {
		if(value == null) {
			value = defaultValue;
		}
		if(value != null && StringUtil.notEmpty(ognlExpression)) {
			// TODO 使用OGNL表达式
//			actualValue = OGNL
		}
		this.value = value;
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
	public boolean isRequired() {
		return required;
	}
	public Object getDefaultValue() {
		return defaultValue;
	}
	public String getDescription() {
		return description;
	}
}
