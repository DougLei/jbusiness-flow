package com.douglei.business.flow.executer.sql.component;

import com.douglei.business.flow.executer.parameter.Parameter;
import com.douglei.business.flow.executer.sql.SqlData;
import com.douglei.business.flow.executer.sql.component.select.Select;

/**
 * 
 * @author DougLei
 */
public class Value implements Component{
	private String column;
	private Object value; 
	private Parameter parameter;
	private boolean placeholder;
	private byte package_;
	private Function function;
	private Select[] selects;
	
	public void setColumn(String column) {
		this.column = column;
	}
	public void setValue(Object value, Boolean placeholder, byte package_) {
		this.value = value;
		setPlaceholder(placeholder);
		setPackage(package_);
	}
	public void setParameter(Parameter parameter, Boolean placeholder, byte package_) {
		this.parameter = parameter;
		setPlaceholder(placeholder);
		setPackage(package_);
	}
	private void setPlaceholder(Boolean placeholder) {
		if(placeholder == null)
			placeholder = true;
		this.placeholder = placeholder;
	}
	private void setPackage(byte package_) {
		if(!this.placeholder) {
			
		}
	}
	public void setFunction(Function function) {
		this.function = function;
	}
	public void setSelects(Select[] selects) {
		this.selects = selects;
	}
	
	@Override
	public void append2SqlData(SqlData sqlData) {
		if(column != null) {
			sqlData.appendSql(column);
		}else if(value != null) {
			
		}else if(parameter != null) {
			
		}else if(function != null) {
			function.append2SqlData(sqlData);
		}else if(selects != null) {
			sqlData.appendSql('(');
			Component.appendComponents2SqlData(selects, sqlData);
			sqlData.appendSql(')');
		}
	}
}
