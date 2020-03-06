package com.douglei.business.flow.executer.action.impl.data.op.arithmetic;

/**
 * 
 * @author DougLei
 */
public class ArithmeticTypeMatchingException extends RuntimeException {
	private static final long serialVersionUID = 7932127572576883786L;

	public ArithmeticTypeMatchingException(String op) {
		super("数据运算的操作符["+op+"]不正确");
	}
}
