package com.douglei.business.flow.executer.sql.component;

import com.douglei.business.flow.executer.condition.ConditionValidator;
import com.douglei.business.flow.executer.sql.SqlData;

/**
 * 
 * @author DougLei
 */
public abstract class Component {
	protected ConditionValidator validator; 
	
	protected Component(ConditionValidator validator) {
		this.validator = validator;
	}

	/**
	 * 给SqlData中追加数据
	 * @param sqlData
	 */
	public abstract void append2SqlData(SqlData sqlData);
	
	/**
	 * 组件数组时, 组件间相连的连接符, 默认为,
	 * @return
	 */
	public String linkSymbol() {
		return ",";
	}
	
	/**
	 * 追加组件数据到SqlData实例中
	 * @param components
	 * @param sqlData
	 */
	public static final void appendComponents2SqlData(Component[] components, SqlData sqlData) {
		for(int i=0;i<components.length;i++) {
			components[i].append2SqlData(sqlData);
			if(i < components.length-1)
				sqlData.appendSql(components[i].linkSymbol());
		}
	}
}
