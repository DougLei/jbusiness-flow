package com.douglei.business.flow.executer.parameter;

import com.douglei.business.flow.executer.DataType;
import com.douglei.tools.instances.ognl.OgnlHandler;
import com.douglei.tools.utils.StringUtil;

/**
 * 参数
 * @author DougLei
 */
public class Parameter implements Cloneable{
	private String name;
	private String ognlExpression; // ognl表达式, 例如name=zhangsan.age, 其中zhangsan为name值, 后面的则是ognl表达式
	private Scope scope;
	private DataType dataType;
	private Object defaultValue;
	private boolean required;
	
	private Object value; // 实际参数的值
	
	// 验证参数的值, 返回验证后的value
	private static Object validateValue(Parameter parameter, Object value) {
		if(value == null)
			value = parameter.defaultValue;
		if(value == null) 
			value = parameter.dataType.defaultValue();
		if(parameter.required && value == null)
			throw new NullPointerException(parameter.scope.getDescription() + "["+parameter.name+"]的初始值不能为空");
		return validateValueDataType(parameter, value);
	}
	
	// 验证参数的值类型是否和配置的匹配, 返回验证后的value
	private static Object validateValueDataType(Parameter parameter, Object value) {
		if(!parameter.dataType.matching(value))
			throw new ClassCastException(parameter.scope.getDescription() + "["+parameter.name+"]的值应为"+parameter.dataType.name()+"类型");
		return parameter.dataType.convert(value);
	}
	
	// 获取OGNL表达式的值
	private static Object getOGNLValue(Parameter parameter, Object value) {
		if(value != null && StringUtil.notEmpty(parameter.ognlExpression))
			return OgnlHandler.singleInstance().getObjectValue(parameter.ognlExpression, value);
		return value;
	}
	
	/**
	 * 根据参数名和参数范围创建参数实例
	 * @param name
	 * @param scope
	 * @return
	 */
	public static Parameter newInstance(String name, Scope scope) {
		return new Parameter(name, scope);
	}
	
	/**
	 * 创建一个有完整信息的参数实例
	 * @param name
	 * @param scope
	 * @param dataType
	 * @param value
	 * @param required
	 * @return
	 */
	public static Parameter newInstance(String name, Scope scope, DataType dataType, Object value, boolean required) {
		if(StringUtil.notEmpty(name)) {
			return new Parameter(name, scope, dataType, value, required);
		}
		return null;
	}
	
	/**
	 * 根据配置的参数以及实际值, 获取一个实参实例
	 * @param configParameter 配置的参数
	 * @param value 实际值
	 * @return
	 */
	public static Parameter newInstance(Parameter configParameter, Object value) {
		value = getOGNLValue(configParameter, value);
		value = validateValue(configParameter, value);
		
		Parameter actualParameter;
		try {
			actualParameter = (Parameter) configParameter.clone();
		} catch (CloneNotSupportedException e) {
			// 手动clone
			actualParameter = new Parameter(configParameter.name, configParameter.scope, configParameter.dataType, configParameter.defaultValue, configParameter.required);
			actualParameter.ognlExpression = configParameter.ognlExpression;
		}
		actualParameter.value = value;
		return actualParameter;
	}
	
	
	private Parameter() {}
	private Parameter(String name, Scope scope) {
		if(StringUtil.isEmpty(name)) {
			throw new NullPointerException("参数名不能为空");
		}
		short dot = (short) name.indexOf(".");
		if(dot > -1) { // 证明是ognl表达式
			this.name = name.substring(0, dot);
			this.ognlExpression = name.substring(dot+1);
		}else {
			this.name = name;
		}
		this.scope = scope;
	}
	private Parameter(String name, Scope scope, DataType dataType, Object defaultValue, boolean required) {
		this(name, scope);
		this.dataType = dataType;
		this.defaultValue = defaultValue;
		this.required = required;
	}
	
	// 修改实际值
	public void updateValue(Object value) {
		value = getOGNLValue(this, value);
		this.value = validateValueDataType(this, value);
	}
	// 修改名字
	public void updateName(String name) {
		this.name = name;
	}
	// 修改范围
	public void updateScope(Scope scope) {
		this.scope = scope;
	}
	
	/**
	 * 根据ognl表达式, 获取对应的值
	 * @param ognlExpression 传入null, 则直接获取value值
	 * @return
	 */
	public Object getValue(String ognlExpression) {
		if(value != null && StringUtil.notEmpty(ognlExpression))
			return OgnlHandler.singleInstance().getObjectValue(ognlExpression, value);
		return value;
	}
	public String getName() {
		return name;
	}
	public String getOgnlExpression() {
		return ognlExpression;
	}
	public Scope getScope() {
		return scope;
	}
	public DataType getDataType() {
		return dataType;
	}
	public Object getDefaultValue() {
		return defaultValue;
	}
	public boolean isRequired() {
		return required;
	}
}