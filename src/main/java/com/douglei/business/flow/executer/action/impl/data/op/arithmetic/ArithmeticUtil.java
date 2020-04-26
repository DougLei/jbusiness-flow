package com.douglei.business.flow.executer.action.impl.data.op.arithmetic;

import com.douglei.business.flow.executer.action.ExecuteParameter;

/**
 * 
 * @author DougLei
 */
class ArithmeticUtil {
	
	static void append(StringBuilder formula, DataOpArithmetic[] group, ExecuteParameter executeParameter) {
		for (int i = 0; i < group.length; i++) {
			group[i].drawFormula(formula, executeParameter);
			if(i < group.length-1) {
				formula.append(group[i].getOp().getOperator());
			}
		}
	}
}
