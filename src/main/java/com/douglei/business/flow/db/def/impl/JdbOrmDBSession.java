package com.douglei.business.flow.db.def.impl;

import java.util.List;
import java.util.Map;

import com.douglei.business.flow.db.DBSession;
import com.douglei.orm.sessionfactory.SessionFactory;
import com.douglei.orm.sessionfactory.sessions.Session;
import com.douglei.orm.sessionfactory.sessions.sqlsession.SqlSession;

/**
 * 
 * @author DougLei
 */
public class JdbOrmDBSession implements DBSession {
	private SessionFactory sessionFactory;
	private Session session;
	private boolean autoCommit;
	
	public JdbOrmDBSession(SessionFactory sessionFactory) {
		this(sessionFactory, true);
	}
	public JdbOrmDBSession(SessionFactory sessionFactory, boolean autoCommit) {
		this.sessionFactory = sessionFactory;
		this.autoCommit = autoCommit;
	}

	private SqlSession getSqlSession() {
		if(session == null)
			session = sessionFactory.openSession();
		return session.getSqlSession();
	}
	
	@Override
	public boolean autoCommit() {
		return autoCommit;
	}

	@Override
	public int executeUpdate(String sql, List<Object> values) {
		return getSqlSession().executeUpdate(sql, values);
	}
	
	@Override
	public Map<String, Object> uniqueQuery(String sql, List<Object> values) {
		return getSqlSession().uniqueQuery(sql, values);
	}
	
	@Override
	public List<Map<String, Object>> query(String sql, List<Object> values) {
		return getSqlSession().query(sql, values);
	}
	
	@Override
	public void commit() {
		if(session != null)
			session.commit();
	}

	@Override
	public void rollback() {
		if(session != null)
			session.rollback();
	}

	@Override
	public void close() {
		if(session != null)
			session.close();
	}
}
