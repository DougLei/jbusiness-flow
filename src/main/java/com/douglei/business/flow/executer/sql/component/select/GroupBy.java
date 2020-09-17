package com.douglei.business.flow.executer.sql.component.select;

import com.douglei.business.flow.executer.condition.ConditionValidator;
import com.douglei.business.flow.executer.sql.SqlData;
import com.douglei.business.flow.executer.sql.component.Function;
import com.douglei.business.flow.executer.sql.component.insert.Column;

/**
 * 
 * @author DougLei
 */
public class GroupBy extends Column{
	private static final long serialVersionUID = -6180084275792263415L;
	private Function function;
	
	public GroupBy(ConditionValidator validator) {
		super(validator);
	}

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
