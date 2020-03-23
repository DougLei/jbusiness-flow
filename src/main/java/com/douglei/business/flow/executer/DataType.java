package com.douglei.business.flow.executer;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public enum DataType {
	STRING(null, String.class),
	NUMBER(0, byte.class, short.class, int.class, long.class, float.class, double.class, Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class),
	BOOLEAN(false, boolean.class, Boolean.class),
	DATE(new Date(), Date.class),
	ARRAY,
	LIST(null, ArrayList.class),
	OBJECT;
	
	private Object defaultValue; // 默认值
	
	private DataType() {}
	private DataType(Object defaultValue, Class<?>... classes) {
		this.defaultValue = defaultValue;
		for (Class<?> clz : classes) {
			DataTypeMapping.CLASS_DATATYPE_MAPPING.put(clz, this);
		}
	}
	
	public Object defaultValue() {
		return defaultValue;
	}

	public static DataType toValue(String value) {
		if(StringUtil.notEmpty(value)) {
			value = value.trim().toUpperCase();
			for(DataType dt : DataType.values()) {
				if(dt.name().equals(value)) {
					return dt;
				}
			}
		}
		return STRING;
	}
	
	/**
	 * 判断传入的值与当前类型是否匹配
	 * 如果当前值为null, 则默认匹配
	 * @param actualValue
	 * @return
	 */
	public boolean matching(Object actualValue) {
		if(actualValue == null || this == OBJECT) 
			return true;
		return (actualValue.getClass().isArray() && this == ARRAY) || DataTypeMapping.CLASS_DATATYPE_MAPPING.get(actualValue.getClass()) == this;
	}
}

class DataTypeMapping {
	static final Map<Class<?>, DataType> CLASS_DATATYPE_MAPPING = new HashMap<Class<?>, DataType>(32); // class与DataType的映射集合
}