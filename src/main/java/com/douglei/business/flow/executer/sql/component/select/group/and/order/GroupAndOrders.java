package com.douglei.business.flow.executer.sql.component.select.group.and.order;

import com.douglei.business.flow.executer.sql.SqlData;
import com.douglei.business.flow.executer.sql.component.Component;
import com.douglei.business.flow.executer.sql.component.Function;

/**
 * 
 * @author DougLei
 */
public class GroupAndOrders extends Component{
	private String prefixSql;
	private GroupAndOrder[] groupAndOrders;
	
	public GroupAndOrders(String prefixSql, byte size) {
		this.prefixSql = prefixSql;
		this.groupAndOrders = new GroupAndOrder[size];
	}
	
	public void initialGroupAndOrder(byte index, Byte sort) {
		this.groupAndOrders[index] = new GroupAndOrder(sort);
	}
	public void setColumn(byte index, String column) {
		this.groupAndOrders[index].setColumn(column);
	}
	public void setFunction(byte index, Function function) {
		this.groupAndOrders[index].setFunction(function);
	}

	@Override
	public void append2SqlData(SqlData sqlData) {
		sqlData.appendSql(prefixSql);
		Component.appendComponents2SqlData(groupAndOrders, sqlData);
	}
}
