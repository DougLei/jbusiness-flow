package com.douglei.business.flow.executer.condition;

import java.util.LinkedList;
import java.util.Queue;

import com.douglei.business.flow.executer.LogicalOP;

/**
 * 
 * @author DougLei
 */
public class ConditionHandler {
	private Queue<LogicalOP> opQueue;
	private LogicalOP lastOp; // 队尾的op实例
	
	private int count; // 记录当前条件队列的数量
	private Queue<Condition> conditionQueue;
	private Condition lastCondition; // 队尾的condition实例
	
	private ConditionValidator conditionValidator;
	
	public ConditionHandler() {
		opQueue = new LinkedList<LogicalOP>();
		conditionQueue = new LinkedList<Condition>();
	}
	
	/**
	 * 堆入操作符
	 * @param op
	 */
	public void pushLogicalOP(LogicalOP op) {
		if(lastOp != null && lastOp != LogicalOP.LEFT_PARENTHESES && op != lastOp) {
			
		}
	}
	
	/**
	 * 堆入条件
	 * @param condition
	 */
	public void pushCondition(Condition condition) {
		count++;
		lastCondition = condition;
		conditionQueue.offer(lastCondition);
	}

	/**
	 * 返回最终的条件验证器实例
	 * @return
	 */
	public ConditionValidator getConditionValidator() {
		return conditionValidator; 
	}
}
