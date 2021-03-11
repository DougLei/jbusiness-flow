package com.douglei.business.flow.db.def.impl;

import java.util.List;
import java.util.Map;

import com.douglei.business.flow.db.DBSession;
import com.douglei.business.flow.db.DBPageResult;
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
	private boolean beginTransaction;
	
	/**
	 * 默认开启事物
	 * @param sessionFactory
	 */
	public JdbOrmDBSession(SessionFactory sessionFactory) {
		this(sessionFactory, true);
	}
	public JdbOrmDBSession(SessionFactory sessionFactory, boolean beginTransaction) {
		this.sessionFactory = sessionFactory;
		this.beginTransaction = beginTransaction;
	}

	private SqlSession getSqlSession() {
		if(session == null)
			session = sessionFactory.openSession(beginTransaction);
		return session.getSqlSession();
	}
	
	@Override
	public boolean beginTransaction() {
		return beginTransaction;
	}
	
	@Override
	public void setBeginTransaction(boolean beginTransaction) {
		this.beginTransaction = beginTransaction;
	}

	@Override
	public int executeUpdate(String sql, List<Object> values) {
		return getSqlSession().executeUpdate(sql, values);
	}
	
	@Override
	public List<Map<String, Object>> query(String sql, List<Object> values) {
		return getSqlSession().query(sql, values);
	}
	
	@Override
	public Map<String, Object> uniqueQuery(String sql, List<Object> values) {
		return getSqlSession().uniqueQuery(sql, values);
	}
	
	@Override
	public DBPageResult pageQuery(int pageNum, int pageSize, String sql, List<Object> values) {
		return new JdbOrmDBPageResult(getSqlSession().pageQuery(pageNum, pageSize, sql, values));
	}
//	@Override
//	public List<Map<String, Object>> recursiveQuery(int deep, String pkColumnName, String parentPkColumnName, Object parentValue, String childNodeName, String sql, List<Object> values) {
//		return getSqlSession().recursiveQuery(deep, pkColumnName, parentPkColumnName, parentValue, childNodeName, sql, values);
//	}
//	@Override
//	public DBPageResult pageRecursiveQuery(int pageNum, int pageSize, int deep, String pkColumnName, String parentPkColumnName, Object parentValue, String childNodeName, String sql, List<Object> values) {
//		return new JdbOrmDBPageResult(getSqlSession().pageRecursiveQuery(pageNum, pageSize, deep, pkColumnName, parentPkColumnName, parentValue, childNodeName, sql, values));
//	}
	
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
