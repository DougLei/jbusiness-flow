package com.douglei.business.flow.executer.parameter;

/**
 * 
 * @author DougLei
 */
public enum Scope {
	// 1
	IN("输入参数"),
	// 2
	INOUT("输入输出参数"),
	// 3
	OUT("输出参数"),
	// 4
	GLOBAL("全局参数"),
	// 5
	LOCAL("本地参数"),
	// 6
	METHOD("方法参数");

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
			case 6:
				return METHOD;
			default:
				return LOCAL;
		}
	}
}
