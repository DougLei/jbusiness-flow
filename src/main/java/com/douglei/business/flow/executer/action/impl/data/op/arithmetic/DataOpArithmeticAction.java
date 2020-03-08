package com.douglei.business.flow.executer.action.impl.data.op.arithmetic;

import com.douglei.business.flow.executer.Parameter;
import com.douglei.business.flow.executer.action.Action;

/**
 * 
 * @author DougLei
 */
public class DataOpArithmeticAction extends Action {
	private DataOpArithmetic[] group;

	public DataOpArithmeticAction(DataOpArithmetic[] group, Parameter result) {
		this.group = group;
		this.result = result;
	}

	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		return null;
	}
}
