package com.douglei.business.flow.executer.parameter;

import com.douglei.tools.utils.StringUtil;

/**
 * 参数
 * @author DougLei
 */
public class Parameter {
	protected String name;
	protected String ognlExpression; // ognl表达式, 例如name=zhangsan.age, 其中zhangsan为name值, 后面的则是ognl表达式
	protected Scope scope;
	protected Object defaultValue;
	
	protected Parameter() {}
	public Parameter(String name, Scope scope, Object defaultValue) {
		this(name, scope, defaultValue, true);
	}
	protected Parameter(String name, Scope scope, Object defaultValue, boolean validateNameNotNull) {
		if(validateNameNotNull && StringUtil.isEmpty(name))
			throw new NullPointerException("参数名不能为空");
		short dot = (short) name.indexOf(".");
		if(dot > -1) { // 证明是ognl表达式
			this.name = name.substring(0, dot);
			this.ognlExpression = name.substring(dot+1);
		}else {
			this.name = name;
		}
		this.scope = scope;
		this.defaultValue = defaultValue;
	}
	
	/**
	 * 修改参数名
	 * @param name
	 */
	public final void updateName(String name) {
		this.name = name;
	}
	/**
	 * 修改参数范围
	 * @param scope
	 */
	public final void updateScope(Scope scope) {
		this.scope = scope;
	}
	
	public final String getName() {
		return name;
	}
	public final String getOgnlExpression() {
		return ognlExpression;
	}
	public final Scope getScope() {
		return scope;
	}
	public Object getDefaultValue() {
		return defaultValue;
	}
}
