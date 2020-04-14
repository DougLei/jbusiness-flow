package com.douglei.business.flow.executer.action.impl.data.op.compare;

import com.douglei.business.flow.db.DBSession;
import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.action.impl.data.op.Data;
import com.douglei.business.flow.executer.parameter.Parameter;

/**
 * 
 * @author DougLei
 */
public class DataOpCompareAction extends Action {
	private CompareType op;
	private Data left;
	private Data right;

	public DataOpCompareAction(CompareType op, Data left, Data right, Parameter result) {
		this.op = op;
		this.left = left;
		this.right = right;
		super.result = result;
	}

	@Override
	public Object execute(DBSession session) {
		return setResult(op.compare(left.getValue(session), right==null?null:right.getValue(session)));
	}
}
