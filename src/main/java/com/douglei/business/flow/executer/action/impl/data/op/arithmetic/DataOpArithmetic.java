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
	
	public Data getData() {
		return data;
	}
	public ArithmeticType getOp() {
		return op;
	}
	public DataOpArithmetic[] getGroup() {
		return group;
	}
	public void setData(Data data) {
		this.data = data;
	}
	public void setGroup(DataOpArithmetic[] group) {
		this.group = group;
	}
}
