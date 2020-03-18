package com.douglei.business.flow.executer.action.impl.data.op.compare;

import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.action.impl.data.op.Data;

/**
 * 
 * @author DougLei
 */
public class DataOpCompareAction extends Action {
	private CompareType op;
	private Data left;
	private Data right;

	public DataOpCompareAction(CompareType op, Data left, Data right) {
		this.op = op;
		this.left = left;
		this.right = right;
	}

	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		return null;
	}
}
