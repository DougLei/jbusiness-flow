package com.douglei.business.flow.db;

/**
 * 
 * @author DougLei
 */
public interface DBSessionFactory {
	
	/**
	 * 创建一个DBSession实例
	 * @return
	 */
	DBSession buildDBSession();
}
