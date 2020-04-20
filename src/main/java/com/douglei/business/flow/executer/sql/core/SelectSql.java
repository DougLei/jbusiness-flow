package com.douglei.business.flow.executer.sql.core;

import com.douglei.business.flow.db.DBSession;
import com.douglei.business.flow.executer.DataType;
import com.douglei.business.flow.executer.parameter.DeclaredParameter;
import com.douglei.business.flow.executer.sql.Sql;
import com.douglei.business.flow.executer.sql.SqlData;
import com.douglei.business.flow.executer.sql.component.Component;
import com.douglei.business.flow.executer.sql.component.select.Select;
import com.douglei.business.flow.executer.sql.component.select.With;

/**
 * 
 * @author DougLei
 */
public class SelectSql extends Sql {
	public static final String TYPE = "select";
	
	private With[] withs;
	private Select[] selects;
	
	public SelectSql(String name, DeclaredParameter[] parameters) {
		super(name, parameters);
	}

	public void setWiths(With[] withs) {
		this.withs = withs;
	}
	public void setSelects(Select[] selects) {
		this.selects = selects;
	}

	@Override
	protected Object invokeCore(DBSession session) {
		SqlData sqlData = new SqlData();
		appendWiths2SqlData(sqlData);
		Component.appendComponents2SqlData(selects, sqlData);
		return executeQuery(sqlData, session);
	}
	private void appendWiths2SqlData(SqlData sqlData) {
		if(withs != null) {
			sqlData.appendSql("WITH ");
			Component.appendComponents2SqlData(withs, sqlData);
		}
	}
	
	/**
	 * 执行查询
	 * @param sqlData
	 * @param session
	 * @return
	 */
	protected Object executeQuery(SqlData sqlData, DBSession session) {
		return session.query(sqlData.getSql(), sqlData.getParameterValues());
	}

	@Override
	public DataType resultDataType() {
		return DataType.LIST;
	}
}
