package com.douglei.business.flow.executer.condition;

import com.douglei.business.flow.executer.LogicalOP;

/**
 * 
 * @author DougLei
 */
public class ConditionUtil {

	/**
	 * 递归验证
	 * @param prevResult 上一个验证的结果
	 * @param prevOP 上一个验证后的逻辑操作符: and/or
	 * @param currentIndex 当前要进行验证的下标
	 * @param conditions 要验证的条件数组
	 * @return
	 */
	public static boolean validate(boolean prevResult, LogicalOP prevOP, int currentIndex, ConditionBase[] conditions) {
		if(currentIndex == conditions.length) {
			return prevResult;
		}
		return validate(prevOP.operating(prevResult, conditions[currentIndex].validate()), conditions[currentIndex].getOp(), currentIndex+1, conditions);
	}
}
