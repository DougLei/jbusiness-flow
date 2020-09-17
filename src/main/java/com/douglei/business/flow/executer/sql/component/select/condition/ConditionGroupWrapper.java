package com.douglei.business.flow.executer.sql.component.select.condition;

import com.douglei.business.flow.executer.condition.ConditionValidator;
import com.douglei.business.flow.executer.sql.SqlData;
import com.douglei.business.flow.executer.sql.component.Component;

/**
 * 
 * @author DougLei
 */
public class ConditionGroupWrapper extends Component {
	private static final long serialVersionUID = 8979786745215238657L;
	private String prefixSql;
	private ConditionGroup[] groups;
	
	public ConditionGroupWrapper(ConditionValidator validator, String prefixSql, ConditionGroup[] groups) {
		super(validator);
		this.prefixSql = prefixSql;
		this.groups = groups;
	}

	@Override
	public void append2SqlData(SqlData sqlData) {
		Component.appendComponents2SqlData(prefixSql, null, groups, sqlData);
	}
}
