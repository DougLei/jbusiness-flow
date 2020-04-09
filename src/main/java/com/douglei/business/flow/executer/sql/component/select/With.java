package com.douglei.business.flow.executer.sql.component.select;

import com.douglei.business.flow.executer.sql.SqlData;
import com.douglei.business.flow.executer.sql.component.Component;

/**
 * 
 * @author DougLei
 */
public class With implements Component{
	private String alias;
	private String[] columns;
	private Select[] selects;
	
	public With(String alias, String[] columns, Select[] selects) {
		this.alias = alias;
		this.columns = columns;
		this.selects = selects;
	}

	@Override
	public void append2SqlData(SqlData sqlData) {
		// TODO Auto-generated method stub
		
	}
}
