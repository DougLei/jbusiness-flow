package com.douglei.business.flow.executer.sql.component;

import com.douglei.business.flow.executer.sql.SqlData;
import com.douglei.business.flow.executer.sql.component.select.Select;

/**
 * 
 * @author DougLei
 */
public class Value implements Component{
	private String column;
	private Object value; 
	private String paramName;
	private boolean placeholder;
	private byte package_;
	private Function function;
	private Select[] selects;
	
	
	public void setColumn(String column) {
		this.column = column;
	}
	public void setValue(Object value, Boolean placeholder, byte package_) {
		this.value = value;
		// TODO 这里要根据值去决定placeholder和package_的值, 下面的paramName同样
	}
	public void setParamName(String paramName, Boolean placeholder, byte package_) {
		this.paramName = paramName;
		// TODO
	}
	public void setFunction(Function function) {
		this.function = function;
	}
	public void setSelects(Select[] selects) {
		this.selects = selects;
	}
	
	@Override
	public void append2SqlData(SqlData sqlData) {
		// TODO Auto-generated method stub
		
	}
}
