package com.douglei.business.flow.executer;

/**
 * 
 * @author DougLei
 */
class ConditionUtil {

	/**
	 * 递归验证
	 * @param prevResult 上一个验证的结果
	 * @param prevOP 上一个验证后的逻辑操作符: and/or
	 * @param currentIndex 当前要进行验证的下标
	 * @return
	 */
	public static boolean validate(ConditionBase[] conditions, boolean prevResult, LogicalOP prevOP, int currentIndex) {
		if(currentIndex == conditions.length) {
			return prevResult;
		}
		return validate(conditions, prevOP.operating(prevResult, conditions[currentIndex].validate()), conditions[currentIndex].getOp(), currentIndex+1);
	}
}
