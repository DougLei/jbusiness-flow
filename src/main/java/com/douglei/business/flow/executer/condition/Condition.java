package com.douglei.business.flow.executer.condition;

import java.util.LinkedList;

import com.douglei.business.flow.executer.LogicalOP;
import com.douglei.business.flow.executer.action.Action;

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
		if(chunkList == null)
			return nextOP;
		return chunkList.getLast().getNextOP();
	}
	
	@Override
	public void pushChunk(Condition condition) {
		if(chunkList == null) 
			chunkList = new LinkedList<ConditionChunk>();
		chunkList.add(condition);
	}
	
	/**
	 * 验证并获取最终的结果(boolean类型)
	 * @return
	 */
	@Override
	public ConditionResult validate() {
		boolean result = (boolean) dataOpCompareAction.execute();
		if(inverse)
			return null;
		return null;
	}
}