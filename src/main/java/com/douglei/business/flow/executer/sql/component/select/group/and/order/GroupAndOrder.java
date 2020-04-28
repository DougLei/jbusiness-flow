package com.douglei.business.flow.executer.sql.component.select.group.and.order;

import com.douglei.business.flow.executer.sql.SqlData;
import com.douglei.business.flow.executer.sql.component.Component;
import com.douglei.business.flow.executer.sql.component.Function;

/**
 * group by 和 order by
 * @author DougLei
 */
class GroupAndOrder extends Component{
	public static final byte ASC = 0;
	public static final byte DESC = 1;
	
	private String column;
	private Function function;
	private Sort sort;

	public GroupAndOrder(Byte sort) {
		if(sort != null)
			this.sort = Sort.toValue(sort);
	}
	
	public void setColumn(String column) {
		this.column = column;
	}
	public void setFunction(Function function) {
		this.function = function;
	}

	@Override
	public void append2SqlData(SqlData sqlData) {
		if(column != null) {
			sqlData.appendSql(column);
		}else if(function != null) {
			function.append2SqlData(sqlData);
		}
		
		if(sort != null)
			sqlData.appendSql(' ').appendSql(sort.sql());
	}
}

enum Sort{
	ASC(" ASC "),
	DESC(" DESC ");
	
	private String sql;
	private Sort(String sql) {
		this.sql = sql;
	}

	public static Sort toValue(byte sort) {
		if(sort == 1)
			return DESC;
		return ASC;
	}

	public String sql() {
		return sql;
	}
}
