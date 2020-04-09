package com.douglei.business.flow.executer.sql;

import com.douglei.business.flow.executer.method.Method;
import com.douglei.business.flow.executer.parameter.Parameter;
import com.douglei.business.flow.executer.sql.component.Component;

/**
 * 
 * @author DougLei
 */
public abstract class Sql extends Method{
	
	protected Sql(String name, Parameter[] parameters) {
		super.name = name;
		super.parameters = parameters;
	}
	
	/**
	 * 追加组件数据到SqlData实例中
	 * @param components
	 * @param sqlData
	 */
	protected void appendComponents2SqlData(Component[] components, SqlData sqlData) {
		for(int i=0;i<components.length;i++) {
			components[i].append2SqlData(sqlData);
			if(i < components.length-1)
				sqlData.appendSql(components[i].linkSymbol());
		}
	}
}
