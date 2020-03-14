package com.douglei.business.flow.executer;

import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public enum DataType {
	STRING,
	BYTE,
	SHORT,
	INTEGER,
	LONG,
	FLOAT,
	DOUBLE,
	BOOLEAN,
	DATE,
	ARRAY,
	LIST,
	OBJECT;

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
	 * 匹配传入的值与当前类型是否匹配
	 * 如果当前值为null, 则默认匹配
	 * @param actualValue
	 * @return
	 */
	public boolean matching(Object actualValue) {
		if(actualValue == null) {
			return true;
		}
		switch(this) {
			case STRING:
				break;
			case BYTE:
				break;
			case STRING:
				break;
			case STRING:
				break;
			case STRING:
				break;
			case STRING:
				break;
			case STRING:
				break;
			case STRING:
				break;
			default:
				break;
		}
		return false;
	}
}
