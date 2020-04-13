package com.douglei.business.flow.executer.sql;

import com.douglei.business.flow.db.Session;
import com.douglei.business.flow.executer.parameter.Parameter;
import com.douglei.business.flow.executer.sql.component.Component;
import com.douglei.business.flow.executer.sql.component.select.Select;
import com.douglei.business.flow.executer.sql.component.select.With;

/**
 * 
 * @author DougLei
 */
public class SelectSql extends Sql {
	public static final byte TYPE = 4; // sql类型: select
	
	private With[] withs;
	private Select[] selects;
	
	public SelectSql(String name, Parameter[] parameters) {
		super(name, parameters);
	}

	public void setWiths(With[] withs) {
		this.withs = withs;
	}
	public void setSelects(Select[] selects) {
		this.selects = selects;
	}

	@Override
	protected Object invokeCore(Session session) {
		SqlData sqlData = new SqlData();
		appendWiths2SqlData(sqlData);
		Component.appendComponents2SqlData(selects, sqlData);
		
		// TODO 具体的jdbc执行SqlData
		sqlData.getSql();
		sqlData.getParameterValues();
		return null;
	}

	private void appendWiths2SqlData(SqlData sqlData) {
		if(withs != null) {
			sqlData.appendSql("WITH ");
			Component.appendComponents2SqlData(withs, sqlData);
		}
	}
}
