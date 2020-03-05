package com.douglei.business.flow.executer.action.impl.data.op.compare;

import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public enum CompareType {
	EQ,
	NE,
	GT,
	GE,
	LT,
	LE,
	BOOL;
	

	
	
	public static CompareType toValue(String value) {
		if(StringUtil.notEmpty(value)) {
			value = value.trim().toUpperCase();
			for(CompareType ct : CompareType.values()) {
				if(ct.name().equals(value)) {
					return ct;
				}
			}
		}
		throw new DataCompareTypeMatchingException(value);
	}
}
