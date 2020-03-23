package com.douglei.business.flow.executer.action.impl.data.op.compare;

import com.douglei.business.flow.executer.action.impl.data.op.DataValue;
import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public enum CompareType {
	EQ("NE"){
		
	},
	NE("EQ"),
	GT("LT"),
	GE("LE"),
	LT("GT"),
	LE("GE"),
	BOOL("NBOOL"){
		@Override
		public boolean compare(DataValue v1, DataValue v2) {
			return (boolean) v1.getValue();
		}
	},
	NBOOL("BOOL"){
		@Override
		public boolean compare(DataValue v1, DataValue v2) {
			return !((boolean) v1.getValue());
		}
	};
	
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
	
	/**
	 * 进行比较
	 * @param v1
	 * @param v2
	 * @return
	 */
	public abstract boolean compare(DataValue v1, DataValue v2);
}
