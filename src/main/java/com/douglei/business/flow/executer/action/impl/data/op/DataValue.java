package com.douglei.business.flow.executer.action.impl.data.op;

import java.util.Date;

import com.douglei.business.flow.executer.DataType;
import com.douglei.tools.utils.StringUtil;
import com.douglei.tools.utils.datatype.dateformat.DateFormatUtil;

/**
 * 
 * @author DougLei
 */
public class DataValue {
	private Object value;
	private DataType dataType;
	private String format;
	
	public static final DataValue NULL_DATA_VALUE = new DataValue();
	private DataValue() {
		setValue(null, null);
	}
	
	
	public DataValue(Object value) {
		setValue(value, null);
	}
	public DataValue(Object value, DataType dataType) {
		if(!dataType.matching(value))
			dataType = null;
		setValue(value, dataType);
	}
	
	private void setValue(Object value, DataType dataType) {
		this.value = value;
		setDataType(value, dataType);
	}
	private void setDataType(Object value, DataType dataType) {
		if(value == null) {
			dataType = DataType.OBJECT;
		}else if(dataType == null) {
			dataType = DataType.toValue(value);
		}
		this.dataType = dataType;
	}
	
	/**
	 * 设置格式
	 * @param format
	 * @return
	 */
	public DataValue setFormat(String format) {
		this.format = format;
		return this;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		
		DataValue other = (DataValue) obj;
		if((value == null && other.value != null) || (value != null && other.value == null)) 
			return false;
		if(value == other.value)
			return true;
		if(dataType.isNumber())
			return  Double.parseDouble(value.toString()) ==  Double.parseDouble(other.value.toString());
		return value.equals(other.value);
	}
	
	/**
	 * 获取数字值
	 * @return
	 */
	public double getNumberValue() {
		if(value == null || !dataType.isNumber()) {
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
		if(dataType.isNumber()) {
			return value.toString();
		}
		if(dataType == DataType.DATE) {
			if(StringUtil.notEmpty(format))
				return DateFormatUtil.format(((Date)value), format);
			return ((Date)value).getTime()+"";
		}
		return "\"" + value.toString() + "\"";
	}
}
