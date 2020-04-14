package com.douglei.business.flow.executer.action.impl.data.op.arithmetic;

import com.douglei.business.flow.db.DBSession;

/**
 * 
 * @author DougLei
 */
class ArithmeticUtil {
	
	static void append(StringBuilder formula, DataOpArithmetic[] group, DBSession session) {
		for (int i = 0; i < group.length; i++) {
			group[i].drawFormula(formula, session);
			if(i < group.length-1) {
				formula.append(group[i].getOp().getOperator());
			}
		}
	}
}
