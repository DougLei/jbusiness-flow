package com.douglei.business.flow.executer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.douglei.tools.utils.StringUtil;
import com.douglei.tools.utils.datatype.VerifyTypeMatchUtil;
import com.douglei.tools.utils.datatype.dateformat.DateFormatUtil;

/**
 * 
 * @author DougLei
 */
public enum DataType {
	STRING(null, String.class, char.class, Character.class){
		@Override
		public Object convert(Object value) {
			if(value == null)
				return null;
			return value.toString();
		}
	},
	INTEGER(0, byte.class, short.class, int.class, long.class, Byte.class, Short.class, Integer.class, Long.class){
		@Override
		public boolean matching(Object value) {
			boolean result = super.matching(value);
			if(!result && value instanceof String) {
				return VerifyTypeMatchUtil.isInteger(value.toString());
			}
			return result;
		}

		@Override
		public Object convert(Object value) {
			if(value == null)
				return null;
			return Long.parseLong(value.toString());
		}
	},
	DOUBLE(0, float.class, double.class, Float.class, Double.class){
		@Override
		public boolean matching(Object value) {
			boolean result = super.matching(value);
			if(!result && value instanceof String) {
				return VerifyTypeMatchUtil.isDouble(value.toString());
			}
			return result;
		}
		
		@Override
		public Object convert(Object value) {
			if(value == null)
				return null;
			return Double.parseDouble(value.toString());
		}
	},
	BOOLEAN(false, boolean.class, Boolean.class){
		@Override
		public boolean matching(Object value) {
			boolean result = super.matching(value);
			if(!result && value instanceof String) {
				return VerifyTypeMatchUtil.isBoolean(value.toString());
			}
			return result;
		}

		@Override
		public Object convert(Object value) {
			if(value == null)
				return null;
			return Boolean.parseBoolean(value.toString());
		}
	},
	DATE(Date.class, java.sql.Date.class, java.sql.Timestamp.class){
		@Override
		public boolean matching(Object value) {
			boolean result = super.matching(value);
			if(!result && (value instanceof String || value instanceof Long)) {
				return DateFormatUtil.verifyIsDate(value.toString());
			}
			return result;
		}

		@Override
		public Object convert(Object value) {
			if(value == null)
				return null;
			return DateFormatUtil.parseDate(value);
		}
	},
	ARRAY(ArrayList.class){
		@Override
		public boolean matching(Object value) {
			boolean result = super.matching(value);
			if(!result) {
				return value instanceof Collection<?>;
			}
			return result;
		}
	},
	OBJECT{
		@Override
		public boolean matching(Object value) {
			return true;
		}
	};
	
	private Object defaultValue; // 默认值
	private DataType() {}
	private DataType(Class<?>... classes) {
		this(null, classes);
	}
	private DataType(Object defaultValue, Class<?>... classes) {
		this.defaultValue = defaultValue;
		for (Class<?> clz : classes)
			DataTypeMapping.CLASS_DATATYPE_MAPPING.put(clz, this);
	}
	
	public Object defaultValue() {
		return defaultValue;
	}

	/**
	 * 根据配置的字符串匹配对应的DataType
	 * @param value
	 * @return
	 */
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
	
//	/**
//	 * 根据值匹配对应的DataType
//	 * @param value 传入非null的值
//	 * @return
//	 */
//	public static DataType toValue(Object value) {
//		if(DateFormatUtil.verifyIsDate(value)) {
//			return DATE;
//		}
//		
//		Class<?> valueClass = value.getClass();
//		DataType dt = DataTypeMapping.CLASS_DATATYPE_MAPPING.get(valueClass);
//		if(dt == null) {
//			if(valueClass.isArray()) {
//				dt = ARRAY;
//			}else if(DateFormatUtil.verifyIsDate(value)) {
//				dt = DATE;
//			}
//		}
//		return dt;
//	}
	
	/**
	 * 判断传入的值与当前类型是否匹配
	 * @param value
	 * @return
	 */
	public boolean matching(Object value) {
		if(value == null)
			return true;
		return DataTypeMapping.CLASS_DATATYPE_MAPPING.get(value.getClass()) == this;
	}
	
	/**
	 * 将值转换为当前类型的数据
	 * <p>这个方法默认值与类型是匹配的</p>
	 * @param value
	 * @return
	 */
	public Object convert(Object value) {
		return value;
	}
}

class DataTypeMapping {
	static final Map<Class<?>, DataType> CLASS_DATATYPE_MAPPING = new HashMap<Class<?>, DataType>(32); // class与DataType的映射集合
}