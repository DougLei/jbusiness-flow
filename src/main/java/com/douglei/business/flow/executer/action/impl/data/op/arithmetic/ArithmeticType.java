package com.douglei.business.flow.executer.action.impl.data.op.arithmetic;

import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public enum ArithmeticType {
	ADD,
	SUB,
	MUL,
	DIV,
	MOD;
	
	public static ArithmeticType toValue(String value) {
		if(StringUtil.notEmpty(value)) {
			value = value.trim().toUpperCase();
			for(ArithmeticType at : ArithmeticType.values()) {
				if(at.name().equals(value)) {
					return at;
				}
			}
		}
		throw new ArithmeticTypeMatchingException(value);
	}
}
