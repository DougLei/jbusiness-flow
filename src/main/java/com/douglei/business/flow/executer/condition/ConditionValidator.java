package com.douglei.business.flow.executer.condition;

import com.douglei.business.flow.executer.LogicalOP;

/**
 * 
 * @author DougLei
 */
public class ConditionValidator {
	private boolean inverse;
	private ConditionValidator left;
	private Condition right;
	private LogicalOP op;
	
	
	public ConditionValidator setRight(LogicalOP op, Condition right) {
		
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
	protected ConditionValidator(Condition left, Condition right, LogicalOP op) {
		this.left = left;
		this.right = right;
		this.op = op;
	}
	private static final ConditionValidator DEFAULT_VALIDATOR = new ConditionValidator();
}
