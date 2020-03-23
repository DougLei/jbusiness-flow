package com.douglei.business.flow.executer.action.impl.data.op;

import com.douglei.business.flow.executer.DataType;

/**
 * 
 * @author DougLei
 */
public class DataValue {
	private Object value;
	private DataType dataType;
	
	public void setValue(Object value) {
		this.value = value;
	}
	public Object getValue() {
		return value;
	}
}
