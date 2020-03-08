package com.douglei.business.flow.executer;

import com.douglei.business.flow.executer.action.Action;

/**
 * 
 * @author DougLei
 */
public class Condition {
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
