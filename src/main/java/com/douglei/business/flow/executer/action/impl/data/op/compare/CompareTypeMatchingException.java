package com.douglei.business.flow.executer.action.impl.data.op.compare;

/**
 * 
 * @author DougLei
 */
public class CompareTypeMatchingException extends RuntimeException {
	private static final long serialVersionUID = 8939582468724175011L;

	public CompareTypeMatchingException(String op) {
		super("数据比较的操作符["+op+"]不正确");
	}
}
