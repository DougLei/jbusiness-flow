package com.douglei.business.flow.executer.condition;

import java.util.Map;

import com.douglei.business.flow.executer.LogicalOP;
import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.parameter.Parameter;

/**
 * 
 * @author DougLei
 */
public class Condition extends ConditionBase{
	private Action dataOpCompareAction;
	
	public Condition(boolean inverse, LogicalOP op, Action dataOpCompareAction) {
		super(inverse, op);
		this.dataOpCompareAction = dataOpCompareAction;
	}
	
	@Override
	protected boolean validate(Map<String, Parameter> localParameterMap) {
		boolean result = (boolean) dataOpCompareAction.execute(localParameterMap);
		if(inverse) {
			return !result;
		}
		return result;
	}
}
