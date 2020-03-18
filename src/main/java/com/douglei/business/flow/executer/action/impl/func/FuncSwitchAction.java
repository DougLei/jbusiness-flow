package com.douglei.business.flow.executer.action.impl.func;

import java.util.Map;

import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.condition.ConditionBase;
import com.douglei.business.flow.executer.condition.ConditionGroup;
import com.douglei.business.flow.executer.parameter.Parameter;

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
		for (SwitchGroup switch_ : groups) {
			if(switch_.validate(localParameterMap)) {
				return switch_.execute(localParameterMap);
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

		boolean validate(Map<String, Parameter> localParameterMap) {
			return ConditionBase.validate(conditionGroups[0].validate(localParameterMap), conditionGroups[0].getOp(), 1, conditionGroups, localParameterMap);
		}

		Object execute(Map<String, Parameter> localParameterMap) {
			for (Action action : actions) {
				action.execute(localParameterMap);
			}
			return null;
		}
	}
}