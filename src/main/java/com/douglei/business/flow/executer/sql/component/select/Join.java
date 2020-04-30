package com.douglei.business.flow.executer.sql.component.select;

import com.douglei.business.flow.executer.condition.ConditionValidator;
import com.douglei.business.flow.executer.sql.SqlData;
import com.douglei.business.flow.executer.sql.component.Component;
import com.douglei.business.flow.executer.sql.component.Table;
import com.douglei.business.flow.executer.sql.component.select.condition.ConditionGroupWrapper;

/**
 * 
 * @author DougLei
 */
public class Join extends Component{
	private Type type;
	private Table table;
	private ConditionGroupWrapper onGroups;
	
	public Join(ConditionValidator validator, byte type, Table table, ConditionGroupWrapper onGroups) {
		super(validator);
		this.type = toValue(type);
		this.table = table;
		this.onGroups = onGroups;
	}
	
	private Type toValue(byte value) {
		if(value == 1)
			return Type.LEFT;
		if(value == 2)
			return Type.RIGHT;
		if(value == 3)
			return Type.FULL;
		return Type.INNER;
	}

	@Override
	public void append2SqlData(SqlData sqlData) {
		sqlData.appendSql(type.sql());
		table.append2SqlData(sqlData);
		onGroups.append2SqlData(sqlData);
	}
}

enum Type{
	INNER, LEFT, RIGHT, FULL;
	
	private String sql;
	private Type() {
		this.sql = " " + name() + " JOIN ";
	}

	public String sql() {
		return sql;
	}
}

