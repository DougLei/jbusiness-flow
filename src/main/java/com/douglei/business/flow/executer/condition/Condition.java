package com.douglei.business.flow.executer.condition;

import java.util.LinkedList;

import com.douglei.business.flow.executer.LogicalOP;
import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.action.ExecuteParameter;

/**
 * 
 * @author DougLei
 */
public class Condition extends ConditionChunk{
	private Action dataOpCompareAction;
	
	public Condition(boolean inverse, LogicalOP nextOP, Action dataOpCompareAction) {
		super(inverse, nextOP);
		this.dataOpCompareAction = dataOpCompareAction;
	}
	
	@Override
	public LogicalOP getNextOP() {
		if(list == null)
			return nextOP;
		return list.getLast().getNextOP();
	}
	
	@Override
	public void appendChunk(Condition condition) {
		if(list == null) 
			list = new LinkedList<ConditionChunk>();
		list.add(condition);
	}
	
	@Override
	public ConditionResult validate(ExecuteParameter executeParameter) {
		boolean result = (boolean) dataOpCompareAction.execute(executeParameter);
		if(inverse)
			result = !result;
		ConditionResult cr = new ConditionResult(result, nextOP);
		if(list != null) 
			list.forEach(l -> cr.merge(l, executeParameter));
		return cr;
	}
}