package com.douglei.business.flow.executer.action.impl.data.op.arithmetic;

import com.douglei.business.flow.executer.action.impl.data.op.Data;

/**
 * 
 * @author DougLei
 */
public class DataOpArithmetic {
	private DataOpArithmetic[] group;
	private Data data;
	private ArithmeticType op;
	
	public DataOpArithmetic(ArithmeticType op) {
		this.op = op;
	}
	
	public void setData(Data data) {
		this.data = data;
	}
	public void setGroup(DataOpArithmetic[] group) {
		this.group = group;
	}
	public ArithmeticType getOp() {
		return op;
	}

	public void drawFormula(StringBuilder formula) {
		if(group != null) {
			formula.append("(");
			ArithmeticUtil.append(formula, group);
			formula.append(")");
		}else {
			formula.append(data.getValue().getStringValue());
		}
	}
}
