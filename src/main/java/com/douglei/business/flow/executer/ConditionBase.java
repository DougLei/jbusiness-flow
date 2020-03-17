package com.douglei.business.flow.executer;

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
	public abstract boolean validate();
}
