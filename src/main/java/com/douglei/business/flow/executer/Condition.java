package com.douglei.business.flow.executer;

import com.douglei.business.flow.executer.action.Action;

/**
 * 
 * @author DougLei
 */
public class Condition {
	public static final byte CONDITION_AND = 0; // 条件: and
	public static final byte CONDITION_OR = 1; // 条件: or
	
	private Action dataOpCompareAction;
	private byte op;
	
	public Condition(Action dataOpCompareAction, byte op) {
		this.dataOpCompareAction = dataOpCompareAction;
		this.op = op;
	}
	
	public Action getDataOpCompareAction() {
		return dataOpCompareAction;
	}
	public byte getOp() {
		return op;
	}
}
