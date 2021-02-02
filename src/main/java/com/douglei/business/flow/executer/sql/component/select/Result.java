package com.douglei.business.flow.executer.sql.component.select;

import com.douglei.business.flow.executer.condition.ConditionValidator;
import com.douglei.business.flow.executer.sql.SqlData;
import com.douglei.business.flow.executer.sql.component.Value;
import com.douglei.tools.StringUtil;

/**
 * 
 * @author DougLei
 */
public class Result extends Value{
	private static final long serialVersionUID = -9039525868101387079L;
	private String alias;
	
	public Result(ConditionValidator validator, String alias) {
		super(validator);
		if(StringUtil.unEmpty(alias))
			this.alias = alias;
	}

	@Override
	public void append2SqlData(SqlData sqlData) {
		super.append2SqlData(sqlData);
		if(alias != null) 
			sqlData.appendSql(" AS ").appendSql(alias);
	}
}
