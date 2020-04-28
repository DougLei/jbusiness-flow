package com.douglei.business.flow.executer.sql.component.select;

import com.douglei.business.flow.executer.sql.SqlData;
import com.douglei.business.flow.executer.sql.component.Function;
import com.douglei.business.flow.executer.sql.component.insert.Column;

/**
 * 
 * @author DougLei
 */
public class GroupBy extends Column{
	private Function function;
	
	public void setFunction(Function function) {
		this.function = function;
	}
	
	public boolean columnIsEmpty() {
		return column == null;
	}
	 
	@Override
	public void append2SqlData(SqlData sqlData) {
		if(column != null) {
			sqlData.appendSql(column);
		}else if(function != null) {
			function.append2SqlData(sqlData);
		}
	}
}
