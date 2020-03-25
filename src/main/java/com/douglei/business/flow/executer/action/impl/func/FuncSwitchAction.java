package com.douglei.business.flow.executer.action.impl.func;

import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.condition.ConditionGroup;
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
	public void add(byte i, ConditionGroup[] conditionGroups, Action[] actions) {
		this.groups[i] = new SwitchGroup(conditionGroups, actions);
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
		private ConditionGroup[] conditionGroups;
		private Action[] actions;
		
		SwitchGroup(ConditionGroup[] conditionGroups, Action[] actions) {
			this.conditionGroups = conditionGroups;
			this.actions = actions;
		}

		boolean validate() {
			return new ConditionValidator(conditionGroups).validate();
		}

		Object execute() {
			for (Action action : actions) {
				action.execute();
			}
			return null;
		}
	}
}