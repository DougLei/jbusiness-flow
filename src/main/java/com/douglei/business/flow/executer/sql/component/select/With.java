package com.douglei.business.flow.executer.sql.component.select;

import com.douglei.business.flow.executer.sql.SqlData;
import com.douglei.business.flow.executer.sql.component.Component;

/**
 * 
 * @author DougLei
 */
public class With implements Component{
	private String alias;
	private String[] columns;
	private Select[] selects;
	
	public With(String alias, String[] columns, Select[] selects) {
		this.alias = alias;
		this.columns = columns;
		this.selects = selects;
	}

	@Override
	public void append2SqlData(SqlData sqlData) {
		sqlData.appendSql(alias);
		
		if(columns != null) {
			sqlData.appendSql('(');
			for(int i=0;i<columns.length;i++) {
				sqlData.appendSql(columns[i]);
				if(i<columns.length-1) {
					sqlData.appendSql(',');
				}
			}
			sqlData.appendSql(')');
		}
		
		sqlData.appendSql(" AS (");
		Component.appendComponents2SqlData(selects, sqlData);
		sqlData.appendSql(')');
	}
}
