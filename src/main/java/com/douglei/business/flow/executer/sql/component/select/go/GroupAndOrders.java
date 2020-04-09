package com.douglei.business.flow.executer.sql.component.select.go;

import com.douglei.business.flow.executer.sql.SqlData;
import com.douglei.business.flow.executer.sql.component.Component;
import com.douglei.business.flow.executer.sql.component.Function;

/**
 * 
 * @author DougLei
 */
public class GroupAndOrders implements Component{
	private String prefixSql;
	private GroupAndOrder[] gos;
	
	public GroupAndOrders(String prefixSql, byte size) {
		this.prefixSql = prefixSql;
		this.gos = new GroupAndOrder[size];
	}
	
	public void initialGroupAndOrder(byte index, Byte sort) {
		this.gos[index] = new GroupAndOrder(sort);
	}
	public void setColumn(byte index, String column) {
		this.gos[index].setColumn(column);
	}
	public void setFunction(byte index, Function function) {
		this.gos[index].setFunction(function);
	}

	@Override
	public void append2SqlData(SqlData sqlData) {
		sqlData.appendSql(prefixSql);
		Component.appendComponents2SqlData(gos, sqlData);
	}
}
