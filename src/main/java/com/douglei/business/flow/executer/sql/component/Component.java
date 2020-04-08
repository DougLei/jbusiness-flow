package com.douglei.business.flow.executer.sql.component;

import com.douglei.business.flow.executer.sql.SqlData;

/**
 * 
 * @author DougLei
 */
public interface Component {
	
	/**
	 * 给SqlData中追加数据
	 * @param sqlData
	 */
	void append2SqlData(SqlData sqlData);
}
