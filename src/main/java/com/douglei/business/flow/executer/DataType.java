package com.douglei.business.flow.executer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public enum DataType {
	STRING(null, String.class, char.class, Character.class),
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
		return OBJECT;
	}
	
	/**
	 * 根据值匹配对应的DataType
	 * @param value 传入非null的值
	 * @return
	 */
	public static DataType toValue(Object value) {
		Class<?> valueClass = value.getClass();
		DataType dt = DataTypeMapping.CLASS_DATATYPE_MAPPING.get(valueClass);
		if(dt == null) {
			if(valueClass.isArray()) {
				dt = ARRAY;
			}else {
				dt = OBJECT;
			}
		}
		return dt;
	}
	
	/**
	 * 判断传入的值与当前类型是否匹配
	 * @param value 传入非null的值
	 * @return
	 */
	public boolean matching(Object value) {
		return toValue(value) == this;
	}
}

class DataTypeMapping {
	static final Map<Class<?>, DataType> CLASS_DATATYPE_MAPPING = new HashMap<Class<?>, DataType>(32); // class与DataType的映射集合
}