package com.douglei.business.flow.executer.sql.component;

import com.douglei.business.flow.executer.sql.SqlData;
import com.douglei.business.flow.executer.sql.component.select.Select;
import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public class Table implements Component{
	private String alias;
	private String name;
	
	private String paramName;
	private Function function;
	private Select[] selects;
	
	public Table(String alias) {
		if(StringUtil.notEmpty(alias)) {
			this.alias = alias;
		}
	}

	public void setName(String name) {
		this.name = name;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
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
