package com.douglei.business.flow.executer.action.impl.data.op.arithmetic;

import java.util.Map;

import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.parameter.Parameter;

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
	public Object execute(Map<String, Parameter> localParameterMap) {
		// TODO Auto-generated method stub
		
		return null;
	}
}
