package com.douglei.business.flow.executer.action.impl.func;

import java.util.Map;

import com.douglei.business.flow.executer.Parameter;
import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.condition.ConditionGroup;

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
	public Object execute(Map<String, Parameter> localParameterMap) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	private class SwitchGroup{
		private ConditionGroup[] conditionGroups;
		private Action[] actions;
		
		SwitchGroup(ConditionGroup[] conditionGroups, Action[] actions) {
			this.conditionGroups = conditionGroups;
			this.actions = actions;
		}



		void execute() {
			// TODO Auto-generated method stub
		}
	}
}