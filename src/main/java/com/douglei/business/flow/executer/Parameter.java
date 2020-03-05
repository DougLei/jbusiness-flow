package com.douglei.business.flow.executer;

/**
 * 参数
 * @author DougLei
 */
public class Parameter {
	private String name;
	private String description;
	private byte scope;
	private DataType dataType;
	private Object defaultValue;
	private boolean required;
	
	public Parameter(String name,String description, byte scope, String dataType, Object defaultValue, Boolean required) {
		this.name = name;
		this.description = description;
		this.scope = scope;
		this.dataType = DataType.toValue(dataType);
		this.defaultValue = defaultValue;
		this.required = required==null?true:required;
	}
	
	private Object value;
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
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
	public DataType getDataType() {
		return dataType;
	}
	public Object getDefaultValue() {
		return defaultValue;
	}
	public boolean isRequired() {
		return required;
	}
}
