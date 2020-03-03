package com.douglei.business.flow.core.param;

import com.douglei.business.flow.Constants;

/**
 * 参数
 * @author DougLei
 */
public class Parameter {
	private String name;
	private String description;
	/**
	 * {@link Constants}
	 */
	private byte scope;
	/**
	 * {@link Constants}
	 */
	private byte dataType;
	private Object defaultValue;
	private boolean required;
	
	public Parameter() {}
	public Parameter(String name, String description, byte scope, byte dataType, Object defaultValue, Boolean required) {
		this.name = name;
		this.description = description;
		this.scope = (scope<1 || scope>5)?Constants.PARAM_SCOPE_LOCAL:scope;
		this.dataType = (dataType<1 || dataType>12)?Constants.DATATYPE_STRING:dataType;
		this.defaultValue = defaultValue;
		this.required = required==null?true:required;
	}
	
	public byte getDataType() {
		return dataType;
	}
	public void setDataType(byte dataType) {
		this.dataType = dataType;
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
	public Object getDefaultValue() {
		return defaultValue;
	}
	public boolean isRequired() {
		return required;
	}
}
