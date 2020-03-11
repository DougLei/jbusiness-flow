package com.douglei.business.flow.executer.sql.component.select;

import com.douglei.business.flow.executer.sql.component.Table;

/**
 * 
 * @author DougLei
 */
public class Select {
	public static final byte UNION = 0;
	public static final byte UNION_ALL = 1;
	
	
	private Result[] results;
	private Table table;
	// join
	// where
	private GroupAndOrder[] groupBys;
	// having
	private GroupAndOrder[] orderBys;
	private byte union;

	public Select(byte union) {
		this.union = union;
	}

	public void setResults(Result[] results) {
		this.results = results;
	}
	public void setTable(Table table) {
		this.table = table;
	}
	public void setGroupBys(GroupAndOrder[] groupBys) {
		this.groupBys = groupBys;
	}
	public void setOrderBys(GroupAndOrder[] orderBys) {
		this.orderBys = orderBys;
	}
}
