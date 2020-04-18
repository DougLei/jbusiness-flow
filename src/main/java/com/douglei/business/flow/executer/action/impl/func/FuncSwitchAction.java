package com.douglei.business.flow.executer.action.impl.func;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.business.flow.db.DBSession;
import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.condition.ConditionValidator;

/**
 * 
 * @author DougLei
 */
public class FuncSwitchAction extends Action {
	private static final Logger logger = LoggerFactory.getLogger(FuncSwitchAction.class);
	private SwitchGroup[] groups;
	
	public FuncSwitchAction(int size) {
		this.groups = new SwitchGroup[size];
	}
	public void add(byte i, ConditionValidator conditionValidator, Action[] actions) {
		this.groups[i] = new SwitchGroup(conditionValidator, actions);
	}

	@Override
	public Object execute(DBSession session) {
		if(logger.isDebugEnabled())
			logger.debug("执行[{}]", getClass().getName());
		for(SwitchGroup switch_ : groups) {
			if(switch_.validate(session)) {
				return switch_.execute(session);
			}
		}
		return null;
	}
	
	private class SwitchGroup{
		private ConditionValidator conditionValidator;
		private Action[] actions;
		
		SwitchGroup(ConditionValidator conditionValidator, Action[] actions) {
			this.conditionValidator = conditionValidator;
			this.actions = actions;
		}

		boolean validate(DBSession session) {
			return conditionValidator.validate(session);
		}

		Object execute(DBSession session) {
			for (Action action : actions) {
				action.execute(session);
			}
			return null;
		}
	}
}