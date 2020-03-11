package com.douglei.business.flow.executer;

import com.douglei.tools.utils.StringUtil;

/**
 * 参数
 * @author DougLei
 */
public class Parameter {
	public static final byte SCOPE_IN = 1; // 参数范围: 输入
	public static final byte SCOPE_INOUT = 2; // 参数范围: 输入输出
	public static final byte SCOPE_OUT = 3; // 参数范围: 输出
	public static final byte SCOPE_GLOBAL = 4; // 参数范围: 全局
	public static final byte SCOPE_LOCAL = 5; // 参数范围: 本地
	
	private String name;
	private byte scope;
	private DataType dataType;
	private boolean required;
	private Object value;
	private String description;
	
	
	private static boolean validate(String name, byte scope) {
		return StringUtil.notEmpty(name) && scope >= SCOPE_IN && scope <= SCOPE_LOCAL;
	}
	
	public static Parameter newInstance(String name, byte scope) {
		if(validate(name, scope)) {
			return new Parameter(name, scope);
		}
		return null;
	}
	public static Parameter newInstance(String name, byte scope, String dataType, Boolean required, Object value, String description) {
		if(validate(name, scope)) {
			return new Parameter(name, scope, dataType, required, value, description);
		}
		return null;
	}
	
	private Parameter(String name, byte scope) {
		this.name = name;
		this.scope = scope;
	}
	private Parameter(String name, byte scope, String dataType, Boolean required, Object value, String description) {
		this(name, scope);
		this.dataType = DataType.toValue(dataType);
		this.required = required==null?true:required;
		this.value = value;
		this.description = description;
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
	public Object getValue() {
		return value;
	}
	public String getDescription() {
		return description;
	}
}
