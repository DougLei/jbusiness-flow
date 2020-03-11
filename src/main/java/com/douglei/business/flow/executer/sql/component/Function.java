package com.douglei.business.flow.executer.sql.component;

/**
 * 
 * @author DougLei
 */
public class Function {
	private String name; // 函数名
	private Value[] values;
	
	public Function(String name) {
		this.name = name;
	}
	public void setValues(Value[] values) {
		this.values = values;
	}
}
