package com.douglei.business.flow.executer.action.impl.data.op.compare;

/**
 * 
 * @author DougLei
 */
public class DataCompareTypeMatchingException extends RuntimeException {
	private static final long serialVersionUID = -2736818006705753584L;

	public DataCompareTypeMatchingException(String op) {
		super("数据比较的操作符["+op+"]不正确");
	}
}
