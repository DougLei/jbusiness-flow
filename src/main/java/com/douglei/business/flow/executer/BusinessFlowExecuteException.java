package com.douglei.business.flow.executer;

/**
 * 业务流执行异常
 * @author DougLei
 */
public class BusinessFlowExecuteException extends RuntimeException {
	private static final long serialVersionUID = -644644889285260988L;

	public BusinessFlowExecuteException(String name, Throwable e) {
		super(name + "流程执行出现异常", e);
	}
}
