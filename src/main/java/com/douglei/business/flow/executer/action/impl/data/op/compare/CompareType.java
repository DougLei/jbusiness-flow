package com.douglei.business.flow.executer.action.impl.data.op.compare;

import com.douglei.business.flow.executer.action.impl.data.op.DataValue;
import com.douglei.tools.StringUtil;

/**
 * 
 * @author DougLei
 */
public enum CompareType {
	EQ("NE"){
		@Override
		public boolean compare(DataValue v1, DataValue v2) {
			return v1.equals(v2);
		}
	},
	NE("EQ") {
		@Override
		public boolean compare(DataValue v1, DataValue v2) {
			return !v1.equals(v2);
		}
	},
	GT("LT") {
		@Override
		public boolean compare(DataValue v1, DataValue v2) {
			return v1.getNumberValue() > v2.getNumberValue();
		}
	},
	GE("LE") {
		@Override
		public boolean compare(DataValue v1, DataValue v2) {
			return v1.getNumberValue() >= v2.getNumberValue();
		}
	},
	LT("GT") {
		@Override
		public boolean compare(DataValue v1, DataValue v2) {
			return v1.getNumberValue() < v2.getNumberValue();
		}
	},
	LE("GE") {
		@Override
		public boolean compare(DataValue v1, DataValue v2) {
			return v1.getNumberValue() <= v2.getNumberValue();
		}
	},
	BOOL("NBOOL"){
		@Override
		public boolean compare(DataValue v1, DataValue v2) {
			return v1.getBooleanValue();
		}
	},
	NBOOL("BOOL"){
		@Override
		public boolean compare(DataValue v1, DataValue v2) {
			return !v1.getBooleanValue();
		}
	};
	
	private String inversion;
	private CompareType(String inversion) {
		this.inversion = inversion;
	}
	
	public static CompareType toValue(String value) {
		if(StringUtil.unEmpty(value)) {
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
