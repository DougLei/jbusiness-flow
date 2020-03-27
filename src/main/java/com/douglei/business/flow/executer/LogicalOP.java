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

		@Override
		public short getPriority() {
			return 1;
		}
	},
	OR {
		@Override
		public boolean operating(boolean bool1, boolean bool2) {
			return bool1 || bool2;
		}

		@Override
		public short getPriority() {
			return 0;
		}
	},
	LEFT_PARENTHESES { // 左括号
		@Override
		public short getPriority() {
			return 3;
		}
	}, 
	RIGHT_PARENTHESES { // 右括号
		@Override
		public short getPriority() {
			return 3;
		}
	},
	NOT{ //取反
		@Override
		public short getPriority() {
			return 2;
		}
	};
	
	/**
	 * 获取对应的逻辑操作符
	 * @param value
	 * @return
	 */
	public static LogicalOP toValue(byte value) {
		return value==0?AND:OR;
	}

	/**
	 * 获取优先级, 值越大, 优先级越高
	 * @return
	 */
	public abstract short getPriority();
	
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
