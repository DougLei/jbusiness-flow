package com.douglei.business.flow.executer.sql.component;

import com.douglei.business.flow.executer.action.ExecuteParameter;
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
	 * 验证组件是否通过
	 * @param executeParameter
	 * @return
	 */
	protected final boolean validate(ExecuteParameter executeParameter) {
		return validator.validate(executeParameter);
	}
	
	/**
	 * 组件数组时, 组件间相连的连接符, 默认为,
	 * @return
	 */
	public String linkSymbol() {
		return ",";
	}
	
	/**
	 * 追加组件数据到SqlData实例中
	 * @param prefix
	 * @param suffix
	 * @param components
	 * @param sqlData
	 * @return 是否有验证通过的组件, 1是0否
	 */
	public static final byte appendComponents2SqlData(String prefix, String suffix, Component[] components, SqlData sqlData) {
		boolean first = true;
		for (Component component : components) {
			if(component.validate(sqlData.getExecuteParameter())) {
				if(!first)
					sqlData.appendSql(component.linkSymbol());
				if(first) {
					first = false;
					if(prefix != null)
						sqlData.appendSql(prefix);
				}
				component.append2SqlData(sqlData);
			}
		}
		
		first = !first; // 将first反转
		if(first && suffix != null)
			sqlData.appendSql(suffix);
		return (byte)(first?1:0);
	}
}
