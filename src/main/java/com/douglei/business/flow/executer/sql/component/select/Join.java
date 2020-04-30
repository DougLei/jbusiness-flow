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
		this.type = Type.toValue(type);
		this.table = table;
		this.onGroups = onGroups;
	}

	@Override
	public void append2SqlData(SqlData sqlData) {
		sqlData.appendSql(type.sql());
		table.append2SqlData(sqlData);
		onGroups.append2SqlData(sqlData);
	}
}

enum Type{
	INNER(" INNER JOIN "),
	LEFT(" LEFT JOIN "),
	RIGHT(" RIGHT JOIN "),
	FULL(" FULL JOIN ");
	
	private String sql;
	private Type(String sql) {
		this.sql = sql;
	}

	static Type toValue(byte value) {
		if(value == 1)
			return LEFT;
		if(value == 2)
			return RIGHT;
		if(value == 3)
			return FULL;
		return INNER;
	}

	public String sql() {
		return sql;
	}
}

