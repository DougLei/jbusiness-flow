package com.douglei.business.flow.executer.parameter;

import com.douglei.business.flow.executer.DataType;
import com.douglei.tools.utils.StringUtil;

/**
 * 参数
 * @author DougLei
 */
public class Parameter implements Cloneable{
	private String name;
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
	
	public static Parameter newInstance(Parameter originParameter, Object actualValue) {
		Parameter actualParameter;
		try {
			actualParameter = (Parameter) originParameter.clone();
		} catch (CloneNotSupportedException e) {
			actualParameter = new Parameter(originParameter.name, originParameter.scope, originParameter.dataType, originParameter.required, originParameter.defaultValue, originParameter.description);
		}
		actualParameter.value = actualValue==null?actualParameter.defaultValue:actualValue;
		return actualParameter;
	}
	
	private Parameter(String name, Scope scope) {
		this.name = name;
		this.scope = scope;
	}
	private Parameter(String name, Scope scope, DataType dataType, boolean required, Object defaultValue, String description) {
		this(name, scope);
		this.dataType = dataType;
		this.required = required;
		this.defaultValue = defaultValue;
		this.description = description;
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

	// 是否是输入输出参数
	public boolean isInputOutParameter() {
		return scope == Scope.INOUT;
	}
}
