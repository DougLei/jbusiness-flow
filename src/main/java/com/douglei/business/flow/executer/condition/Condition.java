package com.douglei.business.flow.executer.condition;

import com.douglei.business.flow.executer.LogicalOP;
import com.douglei.business.flow.executer.action.Action;

/**
 * 
 * @author DougLei
 */
public class Condition {
	private boolean inverse;
	private Action dataOpCompareAction;
	private LogicalOP nextOP;
	
	public Condition(boolean inverse, Action dataOpCompareAction, LogicalOP nextOP) {
		this.inverse = inverse;
		this.dataOpCompareAction = dataOpCompareAction;
		this.nextOP = nextOP;
	}
	
	/**
	 * 验证并获取最终的结果(boolean类型)
	 * @return
	 */
	public boolean validate() {
		boolean result = (boolean) dataOpCompareAction.execute();
		if(inverse) {
			return !result;
		}
		return result;
	}
}