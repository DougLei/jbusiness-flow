package com.douglei.business.flow.executer.action.impl.data.op.arithmetic;

/**
 * 
 * @author DougLei
 */
class ArithmeticUtil {
	
	static void append(StringBuilder formula, DataOpArithmetic[] group) {
		for (int i = 0; i < group.length; i++) {
			group[i].drawFormula(formula);
			if(i < group.length-1) {
				formula.append(group[i].getOp().getOperator());
			}
		}
	}
}
