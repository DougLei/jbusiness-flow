package com.douglei.business.flow.executer.sql;

import com.douglei.business.flow.executer.parameter.Parameter;
import com.douglei.business.flow.executer.sql.component.With;
import com.douglei.business.flow.executer.sql.component.select.Select;

/**
 * 
 * @author DougLei
 */
public class SelectSql extends Sql {
	public static final byte TYPE = 4; // sql类型: select
	
	private With[] withs;
	private Select[] selects;
	
	public SelectSql(String name, Parameter[] parameters) {
		super(name, parameters);
	}

	
	public void setWiths(With[] withs) {
		this.withs = withs;
	}
	public void setSelects(Select[] selects) {
		this.selects = selects;
	}
}
