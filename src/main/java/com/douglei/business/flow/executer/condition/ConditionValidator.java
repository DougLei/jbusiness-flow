package com.douglei.business.flow.executer.condition;

import java.util.List;

import com.douglei.business.flow.executer.LogicalOP;

/**
 * 
 * @author DougLei
 */
public class ConditionValidator {
	private List<ConditionGroup> group;
	
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
	ConditionValidator() {}
	private static final ConditionValidator DEFAULT_VALIDATOR = new ConditionValidator();
}
