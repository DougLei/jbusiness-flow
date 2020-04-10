package com.douglei.business.flow.executer;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.douglei.tools.utils.StringUtil;
import com.douglei.tools.utils.datatype.VerifyTypeMatchUtil;
import com.douglei.tools.utils.datatype.dateformat.DateFormatUtil;

/**
 * 
 * @author DougLei
 */
public enum DataType {
	DATE(){
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
			if(isInstance(value))
				return value;
			return DateFormatUtil.parseDate(value);
		}
		
		@Override
		protected boolean isInstance(Object value) {
			return value instanceof Date;
		}
	},
	INTEGER(0){
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
			return value instanceof Integer || value instanceof Short || value instanceof Long || value instanceof Byte;
		}
	},
	DOUBLE(0){
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
			return value instanceof Float || value instanceof Double || value instanceof BigDecimal;
		}
	},
	BOOLEAN(false){
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
			if(isInstance(value))
				return value;
			return Boolean.parseBoolean(value.toString());
		}
		
		@Override
		protected boolean isInstance(Object value) {
			return value instanceof Boolean;
		}
	},
	STRING(){
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
			return value instanceof String || value instanceof Character;
		}
	},
	ARRAY(){
		@Override
		public boolean matching(Object value) {
			boolean result = super.matching(value);
			if(!result) {
				return value instanceof Collection<?>;
			}
			return result;
		}
		
		@Override
		protected boolean isInstance(Object value) {
			return value instanceof Collection;
		}
	},
	OBJECT(){
		@Override
		public boolean matching(Object value) {
			return true;
		}
		
		@Override
		protected boolean isInstance(Object value) {
			return value instanceof Map || value instanceof Object;
		}
	};
	
	private Object defaultValue; // 默认值
	private DataType() {}
	private DataType(Object defaultValue) {
		this.defaultValue = defaultValue;
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
	
	/**
	 * 是否是数字类型
	 * @return
	 */
	public boolean isNumber() {
		return false;
	}
}