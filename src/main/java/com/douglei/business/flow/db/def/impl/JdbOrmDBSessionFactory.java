package com.douglei.business.flow.db.def.impl;

import com.douglei.business.flow.db.DBSession;
import com.douglei.business.flow.db.DBSessionFactory;
import com.douglei.orm.sessionfactory.SessionFactory;

/**
 * 
 * @author DougLei
 */
public class JdbOrmDBSessionFactory implements DBSessionFactory {
	private SessionFactory sessionFactory;
	
	public JdbOrmDBSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public DBSession buildDBSession() {
		return new JdbOrmDBSession(sessionFactory);
	}
}
