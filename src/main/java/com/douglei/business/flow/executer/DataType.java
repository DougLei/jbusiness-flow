package com.douglei.business.flow.executer;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.douglei.tools.StringUtil;
import com.douglei.tools.datatype.DataTypeValidateUtil;
import com.douglei.tools.datatype.DateFormatUtil;

/**
 * 
 * @author DougLei
 */
public enum DataType {
	DATE(-1){
		@Override
		public boolean matching(Object value) {
			boolean result = super.matching(value);
			if(!result && (value instanceof String || value instanceof Long || value.getClass() == long.class)) {
				return DataTypeValidateUtil.isDate(value);
			}
			return result;
		}

		@Override
		public Object convert(Object value) {
			if(value == null)
				return null;
			if(isInstance(value))
				return value;
			return DateFormatUtil.parseDate(value);
		}
		
		@Override
		protected boolean isInstance(Object value) {
			return value instanceof Date;
		}
	},
	INTEGER(1, 0){
		@Override
		public boolean matching(Object value) {
			boolean result = super.matching(value);
			if(!result && value instanceof String) {
				return DataTypeValidateUtil.isInteger(value.toString());
			}
			return result;
		}

		@Override
		public Object convert(Object value) {
			if(value == null)
				return null;
			if(isInstance(value))
				return value;
			return Long.parseLong(value.toString());
		}

		@Override
		public boolean isNumber() {
			return true;
		}
		
		@Override
		protected boolean isInstance(Object value) {
			if(!(value instanceof Integer || value instanceof Short || value instanceof Long || value instanceof Byte)) {
				Class<?> clz = value.getClass();
				return clz == int.class || clz == short.class || clz == long.class || clz == byte.class; 
			}
			return true;
		}
	},
	DOUBLE(2, 0.0){
		@Override
		public boolean matching(Object value) {
			boolean result = super.matching(value);
			if(!result && value instanceof String) {
				return DataTypeValidateUtil.isNumber(value.toString());
			}
			return result;
		}
		
		@Override
		public Object convert(Object value) {
			if(value == null)
				return null;
			if(isInstance(value))
				return value;
			return Double.parseDouble(value.toString());
		}

		@Override
		public boolean isNumber() {
			return true;
		}
		
		@Override
		protected boolean isInstance(Object value) {
			boolean result = value instanceof Double || value instanceof BigDecimal || value instanceof Float;
			if(!result) {
				Class<?> clz = value.getClass();
				result = (clz == double.class || clz == float.class); 
			}
			if(!result)
				return INTEGER.isInstance(value);
			return true;
		}
	},
	BOOLEAN(-1, false){
		@Override
		public boolean matching(Object value) {
			boolean result = super.matching(value);
			if(!result && value instanceof String) {
				return DataTypeValidateUtil.isBoolean(value.toString());
			}
			return result;
		}

		@Override
		public Object convert(Object value) {
			if(value == null)
				return null;
			if(isInstance(value))
				return value;
			return Boolean.parseBoolean(value.toString());
		}
		
		@Override
		protected boolean isInstance(Object value) {
			return value instanceof Boolean || value.getClass() == boolean.class;
		}
	},
	STRING(-1){
		@Override
		public Object convert(Object value) {
			if(value == null)
				return null;
			if(isInstance(value))
				return value;
			return value.toString();
		}

		@Override
		protected boolean isInstance(Object value) {
			return value instanceof String || value instanceof Character || value.getClass() == char.class;
		}
	},
	LIST(-1){
		@Override
		protected boolean isInstance(Object value) {
			return value instanceof Collection || value.getClass().isArray();
		}
	},
	OBJECT(10){
		@Override
		public boolean matching(Object value) {
			return true;
		}
		
		@Override
		protected boolean isInstance(Object value) {
			return value instanceof Map || value instanceof Object;
		}
	};
	
	private byte valueScope; // 值范围, 其值越大, 包含的值范围就越大, -1表示没有值范围的概念
	private Object defaultValue; // 默认值
	private DataType(int valueScope) {
		this.valueScope = (byte)valueScope;
	}
	private DataType(int valueScope, Object defaultValue) {
		this(valueScope);
		this.defaultValue = defaultValue;
	}
	
	public byte getValueScope() {
		return valueScope;
	}
	public Object defaultValue() {
		return defaultValue;
	}
	/**
	 * 不存在值范围(的概念)
	 * @return
	 */
	public boolean noValueScope() {
		return valueScope == -1;
	}
	/**
	 * 是否是数字类型
	 * @return
	 */
	public boolean isNumber() {
		return false;
	}

	/**
	 * 根据配置的字符串匹配对应的DataType
	 * @param value
	 * @return
	 */
	public static DataType toValue(String value) {
		if(StringUtil.unEmpty(value)) {
			value = value.trim().toUpperCase();
			for(DataType dt : DataType.values()) {
				if(dt.name().equals(value)) {
					return dt;
				}
			}
			throw new IllegalArgumentException("不存在value="+value+"的dataType值");
		}
		throw new NullPointerException("dataType的值不能为空");
	}
	
	/**
	 * 根据值匹配对应的DataType
	 * @param value 传入非null的值
	 * @return
	 */
	public static DataType toValue(Object value) {
		if(value != null) {
			for (DataType dt: DataType.values()) {
				if(dt.matching(value))
					return dt;
			}
		}
		return OBJECT;
	}
	
	/**
	 * 判断传入的值与当前类型是否匹配
	 * @param value
	 * @return
	 */
	public boolean matching(Object value) {
		if(value == null)
			return true;
		return isInstance(value);
	}
	
	/**
	 * 判断value是否当前类型的实例
	 * @param value
	 * @return
	 */
	protected abstract boolean isInstance(Object value);
	
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