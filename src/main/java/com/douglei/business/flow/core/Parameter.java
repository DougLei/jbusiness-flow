package com.douglei.business.flow.core;

/**
 * 参数
 * @author DougLei
 */
public class Parameter {
	private String name;
	private String description;
	private byte scope;
	private byte dataType;
	private Object defaultValue;
	private boolean required;
	
	public Parameter(String name,String description, byte scope, byte dataType, Object defaultValue, Boolean required) {
		this.name = name;
		this.description = description;
		this.scope = scope;
		this.dataType = dataType;
		this.defaultValue = defaultValue;
		this.required = required==null?true:required;
	}
	
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public byte getScope() {
		return scope;
	}
	public byte getDataType() {
		return dataType;
	}
	public Object getDefaultValue() {
		return defaultValue;
	}
	public boolean isRequired() {
		return required;
	}
}
