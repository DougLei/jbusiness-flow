package com.douglei.business.flow.db.def.impl;

import java.util.List;
import java.util.Map;

import com.douglei.business.flow.db.SessionWrapper;
import com.douglei.orm.sessionfactory.SessionFactory;
import com.douglei.orm.sessionfactory.sessions.Session;

/**
 * 
 * @author DougLei
 */
public class JdbOrmSessionWrapper implements SessionWrapper {
	private SessionFactory sessionFactory;
	private Session session;
	private boolean autoCommit;
	
	public JdbOrmSessionWrapper(SessionFactory sessionFactory, boolean autoCommit) {
		this.sessionFactory = sessionFactory;
		this.autoCommit = autoCommit;
	}

	private Session getSession() {
		if(session == null)
			session = sessionFactory.openSession();
		return session;
	}
	
	@Override
	public boolean autoCommit() {
		return autoCommit;
	}
	
	@Override
	public int executeUpdate(String sql, List<Object> values) {
		return getSession().getSqlSession().executeUpdate(sql, values);
	}

	@Override
	public List<Map<String, Object>> query(String sql, List<Object> values) {
		return getSession().getSqlSession().query(sql, values);
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
