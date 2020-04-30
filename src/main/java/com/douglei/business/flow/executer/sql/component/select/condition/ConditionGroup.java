package com.douglei.business.flow.executer.sql.component.select.condition;

import com.douglei.business.flow.executer.LogicalOP;
import com.douglei.business.flow.executer.condition.ConditionValidator;
import com.douglei.business.flow.executer.sql.SqlData;
import com.douglei.business.flow.executer.sql.component.Component;

/**
 * 
 * @author DougLei
 */
public class ConditionGroup extends Component {
	private ConditionGroup[] conditionGroups;
	private Condition[] conditions;
	private LogicalOP cgcop;
	private LogicalOP op;
	
	public ConditionGroup(ConditionValidator validator, ConditionGroup[] conditionGroups, Condition[] conditions, LogicalOP cgcop, LogicalOP op) {
		super(validator);
		this.conditionGroups = conditionGroups;
		this.conditions = conditions;
		this.cgcop = cgcop;
		this.op = op;
	}

	@Override
	public void append2SqlData(SqlData sqlData) {
		sqlData.appendSql('(');
		if(conditionGroups != null)
			Component.appendComponents2SqlData(conditionGroups, sqlData);
		
		if(conditions != null) {
			if(conditionGroups != null)
				sqlData.appendSql(cgcop.name());
			Component.appendComponents2SqlData(conditions, sqlData);
		}
		sqlData.appendSql(')');
	}

	@Override
	public String linkSymbol() {
		return op.name();
	}
}
