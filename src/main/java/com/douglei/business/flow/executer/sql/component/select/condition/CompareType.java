package com.douglei.business.flow.executer.sql.component.select.condition;

import com.douglei.business.flow.executer.action.impl.data.op.compare.CompareTypeMatchingException;
import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public enum CompareType {
	EQ("NE"),
	NE("EQ"),
	GT("LT"),
	GE("LE"),
	LT("GT"),
	LE("GE"),
	BTN("NBTN"), // between and
	NBTN(null), // not between and
	IN("NIN"), // in
	NIN(null), // not in
	LIKE("NLIKE"), // like
	NLIKE(null), // not like
	NULL("NNULL"), // is null
	NNULL(null); // not is null
	
	private String inversion;
	private CompareType(String inversion) {
		this.inversion = inversion;
	}
	
	public static CompareType toValue(String value) {
		if(StringUtil.notEmpty(value)) {
			value = value.trim().toUpperCase();
			
			boolean isInversion = false;
			if(isInversion = value.charAt(0) == '!') {
				value = value.substring(1);
			}
			for(CompareType ct : CompareType.values()) {
				if(ct.name().equals(value)) {
					if(isInversion) {
						return CompareType.toValue(ct.inversion);
					}
					return ct;
				}
			}
		}
		throw new CompareTypeMatchingException(value);
	}
}
