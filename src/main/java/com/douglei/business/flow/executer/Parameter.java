package com.douglei.business.flow.executer;

import com.douglei.business.flow.Constants;
import com.douglei.tools.utils.StringUtil;

/**
 * 参数
 * @author DougLei
 */
public class Parameter {
	private String name;
	private byte scope;
	private DataType dataType;
	private boolean required;
	private Object value;
	private String description;
	
	
	private static boolean validate(String name, byte scope) {
		return StringUtil.notEmpty(name) && scope >= Constants.PARAM_SCOPE_IN && scope <= Constants.PARAM_SCOPE_LOCAL;
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
