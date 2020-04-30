package com.douglei.business.flow.executer.sql.component.select;

import com.douglei.business.flow.executer.condition.ConditionValidator;
import com.douglei.business.flow.executer.sql.SqlData;
import com.douglei.business.flow.executer.sql.component.Component;
import com.douglei.business.flow.executer.sql.component.insert.Column;

/**
 * 
 * @author DougLei
 */
public class With extends Component{
	private String alias;
	private Column[] columns;
	private Select[] selects;
	
	public With(ConditionValidator validator, String alias, Column[] columns, Select[] selects) {
		super(validator);
		this.alias = alias;
		this.columns = columns;
		this.selects = selects;
	}

	@Override
	public void append2SqlData(SqlData sqlData) {
		sqlData.appendSql(alias);
		if(columns != null) 
			Component.appendComponents2SqlData("(", ")", columns, sqlData);
		Component.appendComponents2SqlData(" AS (", ")", selects, sqlData);
	}
}
