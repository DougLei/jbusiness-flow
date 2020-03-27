package com.douglei.business.flow.executer.condition;

import java.util.Stack;

import com.douglei.business.flow.executer.LogicalOP;

/**
 * 
 * @author DougLei
 */
public class ConditionStack {
	private Stack<LogicalOP> opStack;
	private Stack<ConditionValidator> stack;
	
	public ConditionStack() {
		opStack = new Stack<LogicalOP>();
		stack = new Stack<ConditionValidator>();
	}
	
	/**
	 * 堆入操作符
	 * @param op
	 */
	public void pushLogicalOP(LogicalOP op) {
		if(opStack.isEmpty() ) {
			opStack.push(op);
		} else if(op == LogicalOP.RIGHT_PARENTHESES) {
			
			
			
		}else {
			LogicalOP top = opStack.peek();
			if(top.getPriority() > op.getPriority() && top != LogicalOP.LEFT_PARENTHESES) {
				do {
					top = opStack.pop();
					stack.push(stack.pop().setRight(top, (Condition)stack.pop()));
				}while(!opStack.isEmpty() && (top = opStack.peek()) != LogicalOP.LEFT_PARENTHESES && top.getPriority() > op.getPriority());
			}
			opStack.push(op);
		}
	}
	
	/**
	 * 堆入条件
	 * @param condition
	 */
	public void pushCondition(Condition condition) {
		stack.push(condition);
	}

	/**
	 * 返回最终的条件验证器实例
	 * @return
	 */
	public ConditionValidator getConditionValidator() {
		opStack = null;
		stack = null;
		return null; // TODO 
	}
}
