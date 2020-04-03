package com.douglei.business.flow.executer;

/**
 * 逻辑操作符
 * @author DougLei
 */
public enum LogicalOP {
	AND {
		@Override
		public boolean operating(boolean bool1, boolean bool2) {
			return bool1 && bool2;
		}
	},
	OR {
		@Override
		public boolean operating(boolean bool1, boolean bool2) {
			return bool1 || bool2;
		}
	};
	
	
	public static LogicalOP toValue(byte value) {
		return value==0?AND:OR;
	}

	
	/**
	 * 对bool1和bool2的值进行and/or的运算操作
	 * @param bool1
	 * @param bool2
	 * @return
	 */
	public abstract boolean operating(boolean bool1, boolean bool2);
}
