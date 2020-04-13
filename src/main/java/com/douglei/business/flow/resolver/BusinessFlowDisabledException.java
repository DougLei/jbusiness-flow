package com.douglei.business.flow.resolver;

/**
 * 业务流未激活异常
 * @author DougLei
 */
public class BusinessFlowDisabledException extends RuntimeException {
	private static final long serialVersionUID = -6825344057557803921L;

	public BusinessFlowDisabledException(String name, String description) {
		super(name + "("+description+")流程未被激活, 无法解析");
	}
}
