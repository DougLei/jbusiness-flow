package com.douglei.business.flow.executer.sql.component.select;

import com.douglei.business.flow.executer.condition.ConditionValidator;
import com.douglei.business.flow.executer.sql.SqlData;

/**
 * 
 * @author DougLei
 */
public class OrderBy extends GroupBy {
	private Sort sort;

	public OrderBy(ConditionValidator validator, byte sort) {
		super(validator);
		this.sort = sort==1?Sort.DESC:Sort.ASC;;
	}

	@Override
	public void append2SqlData(SqlData sqlData) {
		super.append2SqlData(sqlData);
		if(sort != null)
			sqlData.appendSql(' ').appendSql(sort.sql());
	}
}

enum Sort{
	ASC, DESC;
	
	private String sql;
	private Sort() {
		this.sql = " " +name()+ " ";
	}

	public String sql() {
		return sql;
	}
}