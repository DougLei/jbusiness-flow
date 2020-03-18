package com.douglei.business.flow.executer.parameter;

/**
 * 参数不存在异常
 * @author DougLei
 */
public class ParameterNotExistsException extends RuntimeException {
	private static final long serialVersionUID = 7253040085917988608L;

	public ParameterNotExistsException(String name, Scope scope) {
		super("在"+scope.getDescription()+"范围中, 未找到名为"+name+"的参数");
	}
}
