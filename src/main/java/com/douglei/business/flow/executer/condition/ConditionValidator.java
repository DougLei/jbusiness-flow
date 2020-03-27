package com.douglei.business.flow.executer.condition;

import com.douglei.business.flow.executer.LogicalOP;

/**
 * 
 * @author DougLei
 */
public class ConditionValidator {
	private boolean inverse;
	private ConditionValidator left;
	private ConditionValidator right;
	private LogicalOP op;
	
	/**
	 * 设置右边的值
	 * @param op
	 * @param right
	 * @return
	 */
	public ConditionValidator setRight(LogicalOP op, ConditionValidator right) {
		return new ConditionValidator(this, op, right);
	}
	
	/**
	 * 取反
	 */
	public void invert() {
		this.inverse = true;
	}
	

	/**
	 * 
	 * @return
	 */
	public boolean validate() {
		// TODO Auto-generated method stub
		return false;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 获取默认的验证器
	 * @return
	 */
	public static final ConditionValidator defaultValidator() {
		return DEFAULT_VALIDATOR;
	}
	protected ConditionValidator() {}
	protected ConditionValidator(ConditionValidator left, LogicalOP op, ConditionValidator right) {
		this.left = left;
		this.op = op;
		this.right = right;
	}
	private static final ConditionValidator DEFAULT_VALIDATOR = new ConditionValidator();
}
