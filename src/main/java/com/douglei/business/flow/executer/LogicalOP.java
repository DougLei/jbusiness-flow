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
	},
	NOT{
		@Override
		public boolean operating(boolean bool1, boolean bool2) {
			return !bool1;
		}
	},
	LEFT_PARENTHESES, // 左括号 
	RIGHT_PARENTHESES; // 右括号
	
	/**
	 * 获取对应的逻辑操作符
	 * @param value
	 * @return
	 */
	public static LogicalOP toValue(byte value) {
		return value==0?AND:OR;
	}
	
	/**
	 * 对bool1和bool2的值进行and/or的运算操作
	 * @param bool1
	 * @param bool2
	 * @return
	 */
	public boolean operating(boolean bool1, boolean bool2) {
		return false;
	}
}
