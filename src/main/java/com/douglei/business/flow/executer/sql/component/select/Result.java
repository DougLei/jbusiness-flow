package com.douglei.business.flow.executer.sql.component.select;

import com.douglei.business.flow.executer.sql.SqlData;
import com.douglei.business.flow.executer.sql.component.Value;
import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public class Result extends Value{
	private String alias;
	
	public Result(String alias) {
		if(StringUtil.notEmpty(alias))
			this.alias = alias;
	}

	@Override
	public void append2SqlData(SqlData sqlData) {
		super.append2SqlData(sqlData);
		if(alias != null) 
			sqlData.appendSql(" AS ").appendSql(alias);
	}
}
