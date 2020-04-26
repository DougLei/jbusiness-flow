package com.douglei.business.flow.executer.action;

import com.douglei.business.flow.db.DBSession;

/**
 * 执行(需要的)参数
 * @author DougLei
 */
public class ExecuteParameter {
	private DBSession session;
	
	public ExecuteParameter(DBSession session) {
		this.session = session;
	}

	public DBSession getSession() {
		return session;
	}
}
