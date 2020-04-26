package com.douglei.business.flow.executer.parameter;

/**
 * 调用者参数, 针对配置调用sql和方法时声明的参数
 * @author DougLei
 */
public class InvokerParameter extends Parameter {
	private String targetName;

	public InvokerParameter(String name, Scope scope, Object defaultValue, String targetName) {
		super(name, false, scope, defaultValue);
		this.targetName = targetName;
	}
	
	public String getTargetName() {
		return targetName;
	}
}
