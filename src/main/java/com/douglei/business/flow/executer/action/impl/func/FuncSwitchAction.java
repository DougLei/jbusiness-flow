package com.douglei.business.flow.executer.action.impl.func;

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
	public Object execute() {
		for(SwitchGroup switch_ : groups) {
			if(switch_.validate()) {
				return switch_.execute();
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

		boolean validate() {
			return conditionValidator.validate();
		}

		Object execute() {
			for (Action action : actions) {
				action.execute();
			}
			return null;
		}
	}
}