package com.douglei.business.flow.executer.action.impl.data.op.arithmetic;

import com.douglei.tools.StringUtil;

/**
 * 
 * @author DougLei
 */
public enum ArithmeticType {
	ADD('+'),
	SUB('-'),
	MUL('*'),
	DIV('/'),
	MOD('%');
	
	private char operator; // 运算符
	private ArithmeticType(char operator) {
		this.operator = operator;
	}

	public static ArithmeticType toValue(String value) {
		if(StringUtil.notEmpty(value)) {
			value = value.trim().toUpperCase();
			for(ArithmeticType at : ArithmeticType.values()) {
				if(at.name().equals(value)) {
					return at;
				}
			}
			throw new ArithmeticTypeMatchingException(value);
		}
		return null;
	}

	public char getOperator() {
		return operator;
	}
}
