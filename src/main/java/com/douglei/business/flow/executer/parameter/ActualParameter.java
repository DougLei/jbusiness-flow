package com.douglei.business.flow.executer.parameter;

import com.douglei.business.flow.executer.DataType;
import com.douglei.tools.instances.ognl.OgnlHandler;
import com.douglei.tools.utils.StringUtil;

/**
 * 实参
 * @author DougLei
 */
public class ActualParameter extends ResultParameter {
	private Object value; // 实参值
	
	public ActualParameter(String name, Scope scope, DataType dataType) {
		super.name = name;
		super.scope = scope;
		super.dataType = dataType;
	}
	
	/**
	 * 设置值
	 * @param required
	 * @param defaultValue
	 * @param value
	 */
	void setValue(boolean required, Object defaultValue, Object value) {
		if(required && value == null)
			throw new NullPointerException(super.scope.getDescription() + "["+super.name+"]的初始值不能为空");
		
		if(value == null) {
			value = defaultValue;
			if(value == null) {
				value = super.dataType.defaultValue();
			}
		}else {
			value =  validateValueDataType(value);
		}
		this.value = value;
	}

	/**
	 * 验证参数的值类型是否和配置的匹配
	 * @param value
	 * @return 返回验证后的value
	 */
	private Object validateValueDataType(Object value) {
		if(!super.dataType.matching(value))
			throw new ClassCastException(super.scope.getDescription() + "["+super.name+"]的值应为"+super.dataType.name()+"类型");
		return super.dataType.convert(value);
	}
	
	/**
	 * 修改参数的实际值
	 * @param value
	 */
	public void updateValue(Object value) {
		this.value = validateValueDataType(value);
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
}
