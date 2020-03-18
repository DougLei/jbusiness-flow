package com.douglei.business.flow.executer.parameter;

/**
 * 
 * @author DougLei
 */
public enum Scope {
	IN("输入参数"),
	INOUT("输入输出参数"),
	OUT("输出参数"),
	GLOBAL("全局参数"),
	LOCAL("本地参数");

	private String description;
	private Scope(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
	
	// 默认是本地参数
	public static Scope toValue(byte scopeValue) {
		switch(scopeValue) {
			case 1:
				return IN;
			case 2:
				return INOUT;
			case 3:
				return OUT;
			case 4:
				return GLOBAL;
			default:
				return LOCAL;
		}
	}
}
