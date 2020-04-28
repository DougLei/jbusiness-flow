package com.douglei.business.flow.executer.sql.component.select;

import com.douglei.business.flow.executer.sql.SqlData;
import com.douglei.business.flow.executer.sql.component.Component;
import com.douglei.business.flow.executer.sql.component.Value;
import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public class Result extends Component{
	private String alias;
	private Value value;
	
	public Result(String alias, Value value) {
		if(StringUtil.notEmpty(alias)) {
			this.alias = alias;
		}
		this.value = value;
	}

	@Override
	public void append2SqlData(SqlData sqlData) {
		value.append2SqlData(sqlData);
		if(alias != null) 
			sqlData.appendSql(" AS ").appendSql(alias);
	}
}
