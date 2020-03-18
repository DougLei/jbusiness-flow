package com.douglei.business.flow.executer.condition;

import java.util.Map;

import com.douglei.business.flow.executer.LogicalOP;
import com.douglei.business.flow.executer.Parameter;

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
	 * @param localParameterMap
	 * @return
	 */
	protected abstract boolean validate(Map<String, Parameter> localParameterMap);
	
	/**
	 * 递归验证条件, 获取最终的结果
	 * @param prevResult 上一个验证的结果
	 * @param prevOP 上一个验证后的逻辑操作符: and/or
	 * @param currentIndex 当前要进行验证的下标
	 * @param conditions 要验证的条件数组
	 * @param localParameterMap 本地参数map
	 * @return
	 */
	public static final boolean validate(boolean prevResult, LogicalOP prevOP, int currentIndex, ConditionBase[] conditions, Map<String, Parameter> localParameterMap) {
		if(currentIndex == conditions.length) {
			return prevResult;
		}
		// 短路功能
		if((prevResult && prevOP==LogicalOP.AND) || (!prevResult && prevOP==LogicalOP.OR)) {
			prevResult = prevOP.operating(prevResult, conditions[currentIndex].validate(localParameterMap));
		}
		return validate(prevResult, conditions[currentIndex].getOp(), currentIndex+1, conditions, localParameterMap);
	}
}
