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
}
