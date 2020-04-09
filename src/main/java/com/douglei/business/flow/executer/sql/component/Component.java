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
	
	/**
	 * 组件数组时, 组件间相连的连接符, 默认为,
	 * @return
	 */
	default String linkSymbol() {
		return ",";
	}
}
