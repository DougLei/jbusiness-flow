package com.douglei.business.flow.executer;

import com.douglei.tools.utils.StringUtil;

/**
 * 参数
 * @author DougLei
 */
public class Parameter implements Cloneable{
	public static final byte SCOPE_IN = 1; // 参数范围: 输入
	public static final byte SCOPE_INOUT = 2; // 参数范围: 输入输出
	public static final byte SCOPE_OUT = 3; // 参数范围: 输出
	public static final byte SCOPE_GLOBAL = 4; // 参数范围: 全局
	public static final byte SCOPE_LOCAL = 5; // 参数范围: 本地
	
	private String name;
	private byte scope;
	private DataType dataType;
	private boolean required = true;
	private Object defaultValue;
	private String description;
	
	private Object value; // 实际参数的值
	
	private static boolean validate(String name, byte scope) {
		return StringUtil.notEmpty(name) && scope >= SCOPE_IN && scope <= SCOPE_LOCAL;
	}
	
	public static Parameter newInstance(String name, byte scope) {
		if(validate(name, scope)) {
			return new Parameter(name, scope);
		}
		return null;
	}
	public static Parameter newInstance(String name, byte scope, DataType dataType, Boolean required, Object value, String description) {
		if(validate(name, scope)) {
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
	
	private Parameter(String name, byte scope) {
		this.name = name;
		this.scope = scope;
	}
	private Parameter(String name, byte scope, DataType dataType, boolean required, Object defaultValue, String description) {
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
	public byte getScope() {
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
		return scope == SCOPE_INOUT;
	}
}
