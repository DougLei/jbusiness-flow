package com.douglei.business.flow.executer;

/**
 * 业务流执行异常
 * @author DougLei
 */
public class BusinessFlowExecuteException extends RuntimeException {
	private static final long serialVersionUID = -2172441661457505203L;

	public BusinessFlowExecuteException(String name, String description, Throwable e) {
		super(name + "("+description+")流程执行出现异常", e);
	}
}
