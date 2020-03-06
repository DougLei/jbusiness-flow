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
	private Object value;
	private boolean required;
	private Parameter refParam;
	
	private Parameter(String name, byte scope) {
		this.name = name;
		this.scope = scope;
	}
	public Parameter(String name, byte scope, String description, String dataType, Object value, Boolean required, String refParamName, byte refParamScope) {
		this(name, scope);
		this.description = description;
		this.dataType = DataType.toValue(dataType);
		this.value = value;
		this.required = required==null?true:required;
		this.refParam = (refParamName==null)?null:new Parameter(refParamName, refParamScope);
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
	public Object getValue() {
		return value;
	}
	public boolean isRequired() {
		return required;
	}
}
