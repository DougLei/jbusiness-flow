package com.douglei.business.flow.executer.action.impl.func;

import com.douglei.business.flow.db.SessionWrapper;
import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.condition.ConditionValidator;

/**
 * 
 * @author DougLei
 */
public class FuncSwitchAction extends Action {
	private SwitchGroup[] groups;
	
	public FuncSwitchAction(int size) {
		this.groups = new SwitchGroup[size];
	}
	public void add(byte i, ConditionValidator conditionValidator, Action[] actions) {
		this.groups[i] = new SwitchGroup(conditionValidator, actions);
	}

	@Override
	public Object execute(SessionWrapper session) {
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

		boolean validate(SessionWrapper session) {
			return conditionValidator.validate(session);
		}

		Object execute(SessionWrapper session) {
			for (Action action : actions) {
				action.execute(session);
			}
			return null;
		}
	}
}