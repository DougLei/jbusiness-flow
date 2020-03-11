package com.douglei.business.flow.executer.sql.component.select;

import com.douglei.business.flow.executer.sql.component.Value;
import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public class Result {
	private String alias;
	private Value value;
	
	public Result(String alias, Value value) {
		if(StringUtil.notEmpty(alias)) {
			this.alias = alias;
		}
		this.value = value;
	}
}
