package com.douglei.business.flow.executer.sql.component.select;

import com.douglei.business.flow.executer.sql.component.Function;

/**
 * group by 和 order by
 * @author DougLei
 */
public class GroupAndOrder {
	public static final byte ASC = 0;
	public static final byte DESC = 1;
	
	private String column;
	private Function function;
	private byte sort;

	public GroupAndOrder(byte sort) {
		this.sort = sort;
	}
	
	public void setColumn(String column) {
		this.column = column;
	}
	public void setFunction(Function function) {
		this.function = function;
	}
}
