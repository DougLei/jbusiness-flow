package com.douglei.business.flow.executer.sql.component;

import com.douglei.business.flow.executer.sql.component.select.Select;

/**
 * 
 * @author DougLei
 */
public class With {
	private String alias;
	private Select[] selects;
	
	public With(String alias, Select[] selects) {
		this.alias = alias;
		this.selects = selects;
	}
}