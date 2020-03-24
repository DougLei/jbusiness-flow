package com.douglei.business.flow.executer.action.impl.data.op;

import com.douglei.business.flow.executer.DataType;
import com.douglei.business.flow.executer.parameter.Parameter;
import com.douglei.tools.utils.ObjectUtil;

/**
 * 
 * @author DougLei
 */
public class DataValue {
	private Object value;
	private DataType dataType;
	
	public DataValue() {
		setValue(null, null);
	}
	public DataValue(Object value) {
		setValue(value, null);
	}
	public DataValue(Parameter parameter) {
		if(parameter == null) {
			parameter = Parameter.emptyParameter();
		}
		setValue(parameter.getValue(), parameter.getDataType());
	}
	
	private void setValue(Object value, DataType dataType) {
		if(value == null) {
			value = ObjectUtil.emptyObject();
			dataType = DataType.OBJECT;
		}
		this.value = value;
		setDataType(dataType);
	}
	private void setDataType(DataType dataType) {
		if(dataType == null) {
			dataType = DataType.toValue(value);
		}
		this.dataType = dataType;
	}
	
	/**
	 * 获取数字值
	 * @return
	 */
	public double getNumberValue() {
		if(value == ObjectUtil.emptyObject() || dataType != DataType.NUMBER) {
			return 0;
		}
		return Double.parseDouble(value.toString());
	}
	
	/**
	 * 获取boolean值
	 * @return
	 */
	public boolean getBooleanValue() {
		if(value == ObjectUtil.emptyObject() || dataType != DataType.BOOLEAN) {
			return false;
		}
		return Boolean.parseBoolean(value.toString());
	}
	
	/**
	 * 获取字符串值
	 * @return
	 */
	public String getStringValue() {
		if(value == ObjectUtil.emptyObject()) {
			return "";
		}
		if(dataType == DataType.NUMBER) {
			return value.toString();
		}
		return "\"" + value.toString() + "\"";
	}
}
