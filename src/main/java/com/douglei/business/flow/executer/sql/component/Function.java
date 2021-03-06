package com.douglei.business.flow.executer.sql.component;

import com.douglei.business.flow.executer.condition.ConditionValidator;
import com.douglei.business.flow.executer.sql.SqlData;

/**
 * 
 * @author DougLei
 */
public class Function extends Component{
	private static final long serialVersionUID = -2346584834276955437L;
	private String name; // 函数名
	private Value[] values;
	
	public Function(ConditionValidator validator, String name) {
		super(validator);
		this.name = name;
	}
	public void setValues(Value[] values) {
		this.values = values;
	}
	
	@Override
	public void append2SqlData(SqlData sqlData) {
		sqlData.appendSql(name);
		Component.appendComponents2SqlData("(", ")", values, sqlData);
	}
}
