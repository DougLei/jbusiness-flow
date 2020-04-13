package com.douglei.business.flow.db.def.impl.jdb.orm;

import java.util.List;
import java.util.Map;

import com.douglei.business.flow.db.SessionWrapper;
import com.douglei.business.flow.executer.sql.SqlData;
import com.douglei.orm.sessionfactory.sessions.sqlsession.SqlSession;

/**
 * 
 * @author DougLei
 */
public class JDBOrmSessionWrapper implements SessionWrapper {
	private SqlSession sqlSession; 
	
	public JDBOrmSessionWrapper(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public int executeUpdate(SqlData sqlData) {
		return sqlSession.executeUpdate(sqlData.getSql(), sqlData.getParameterValues());
	}

	@Override
	public List<Map<String, Object>> query(SqlData sqlData) {
		return sqlSession.query(sqlData.getSql(), sqlData.getParameterValues());
	}
}
