package com.douglei.business.flow.executer;

/**
 * 逻辑操作符
 * @author DougLei
 */
public enum LogicalOP {
	AND,
	OR;
	
	/**
	 * 获取对应的逻辑操作符
	 * @param value
	 * @return
	 */
	public static LogicalOP toValue(byte value) {
		return value==0?AND:OR;
	}
}
