package com.douglei.business.flow.executer.sql.component.select;

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
	
	public With(String alias, Column[] columns, Select[] selects) {
		this.alias = alias;
		this.columns = columns;
		this.selects = selects;
	}

	@Override
	public void append2SqlData(SqlData sqlData) {
		sqlData.appendSql(alias);
		
		if(columns != null) {
			sqlData.appendSql('(');
			Component.appendComponents2SqlData(columns, sqlData);
			sqlData.appendSql(')');
		}
		
		sqlData.appendSql(" AS (");
		Component.appendComponents2SqlData(selects, sqlData);
		sqlData.appendSql(')');
	}
}
