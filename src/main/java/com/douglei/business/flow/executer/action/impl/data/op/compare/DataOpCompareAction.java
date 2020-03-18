package com.douglei.business.flow.executer.action.impl.data.op.compare;

import java.util.Map;

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

	public DataOpCompareAction(CompareType op, Data left, Data right) {
		this.op = op;
		this.left = left;
		this.right = right;
	}

	@Override
	public Object execute(Map<String, Parameter> localParameterMap) {
		// TODO Auto-generated method stub
		return null;
	}
}
