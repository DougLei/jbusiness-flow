package com.douglei.business.flow.executer.action.impl.func;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.action.ExecuteParameter;
import com.douglei.business.flow.executer.condition.ConditionValidator;

/**
 * 
 * @author DougLei
 */
public class FuncSwitchAction extends Action {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(FuncSwitchAction.class);
	private SwitchGroup[] groups;
	
	public FuncSwitchAction(int size) {
		this.groups = new SwitchGroup[size];
	}
	public void add(byte i, ConditionValidator conditionValidator, Action[] actions) {
		this.groups[i] = new SwitchGroup(conditionValidator, actions);
	}

	@Override
	public Object execute(ExecuteParameter executeParameter) {
		if(logger.isDebugEnabled())
			logger.debug("执行[{}]", getClass().getName());
		for(SwitchGroup switch_ : groups) {
			if(switch_.validate(executeParameter)) {
				return switch_.execute(executeParameter);
			}
		}
		return null;
	}
	
	private class SwitchGroup implements Serializable{
		private static final long serialVersionUID = 1L;
		private ConditionValidator conditionValidator;
		private Action[] actions;
		
		SwitchGroup(ConditionValidator conditionValidator, Action[] actions) {
			this.conditionValidator = conditionValidator;
			this.actions = actions;
		}

		boolean validate(ExecuteParameter executeParameter) {
			return conditionValidator.validate(executeParameter);
		}

		Object execute(ExecuteParameter executeParameter) {
			for (Action action : actions) {
				action.execute(executeParameter);
			}
			return null;
		}
	}
}