package com.douglei.business.flow.executer.action.impl.data.op;

import com.douglei.business.flow.executer.DataType;
import com.douglei.business.flow.executer.parameter.Parameter;

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
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		DataValue other = (DataValue) obj;
		if((dataType != other.dataType) || ((value == null && other.value != null) || (value != null && other.value == null))) 
			return false;
		if(value == other.value)
			return true;
		if(dataType == DataType.NUMBER)
			return  Double.parseDouble(value.toString()) ==  Double.parseDouble(other.value.toString());
		if(dataType == DataType.BOOLEAN)
			return (boolean)value == (boolean)other.value;
		return value.equals(other.value);
	}
	
	/**
	 * 获取数字值
	 * @return
	 */
	public double getNumberValue() {
		if(value == null || dataType != DataType.NUMBER) {
			return 0;
		}
		return Double.parseDouble(value.toString());
	}
	
	/**
	 * 获取boolean值
	 * @return
	 */
	public boolean getBooleanValue() {
		if(value == null || dataType != DataType.BOOLEAN) {
			return false;
		}
		return (boolean) value;
	}
	
	/**
	 * 获取字符串值
	 * @return
	 */
	public String getStringValue() {
		if(value == null) {
			return "";
		}
		if(dataType == DataType.NUMBER) {
			return value.toString();
		}
		return "\"" + value.toString() + "\"";
	}
}
