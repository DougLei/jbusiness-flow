package com.douglei.business.flow.executer.condition;

import com.douglei.business.flow.executer.LogicalOP;

/**
 * 
 * @author DougLei
 */
public abstract class ConditionBase {
	protected boolean inverse;
	protected LogicalOP op; 
	
	protected ConditionBase(boolean inverse, LogicalOP op) {
		this.inverse = inverse;
		this.op = op;
	}

	public LogicalOP getOp() {
		return op;
	}

	/**
	 * 对条件进行验证, 返回结果: true/false
	 * @return
	 */
	protected abstract boolean validate();
	
	/**
	 * 递归验证条件, 获取最终的结果
	 * @param prevResult 上一个验证的结果
	 * @param prevOP 上一个验证后的逻辑操作符: and/or
	 * @param currentIndex 当前要进行验证的下标
	 * @param conditions 要验证的条件数组
	 * @return
	 */
	public static final boolean validate(boolean prevResult, LogicalOP prevOP, int currentIndex, ConditionBase[] conditions) {
		if(currentIndex == conditions.length) {
			return prevResult;
		}
		
		// 短路功能:
		// 1.如果prevResult=true, 且prevOP=or, 则最终结果就是true, 即prevResult
		// 2.如果prevResult=false, 且prevOP=and, 则最终结果就是false, 即prevResult
		if((prevResult && prevOP==LogicalOP.OR) || (!prevResult && prevOP==LogicalOP.AND)) {
			return validate(prevResult, conditions[currentIndex].getOp(), currentIndex+1, conditions);
		}else {
			return validate(prevOP.operating(prevResult, conditions[currentIndex].validate()), conditions[currentIndex].getOp(), currentIndex+1, conditions);
		}
	}
}
