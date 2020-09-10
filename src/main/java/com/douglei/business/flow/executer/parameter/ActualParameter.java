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
			throw new NullPointerException(super.scope.getDescription() + "["+super.name+"]的值不能为空");
		
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
		if(this.value != null)
			this.dataType = compareValueScope(dataType, DataType.toValue(this.value));
	}
	
	/**
	 * d1和d2进行值范围比较, 返回值范围更大的DataType
	 * @param d1
	 * @param d2
	 * @return
	 */
	private DataType compareValueScope(DataType d1, DataType d2) {
		if(d1 == d2)
			return d1;
		if(d1 == DataType.OBJECT || d2 == DataType.OBJECT)
			return DataType.OBJECT;
		if(d1.noValueScope() || d2.noValueScope())
			throw new ParameterOPException("["+scope.getDescription()+"]["+name+"]的原数据类型[" + d1.name() + "]和目标数据类型["+d2.name()+"], 没有值范围的可比性, 其值不可相互转换");
		if(d1.getValueScope() > d2.getValueScope()) {
			return d1;
		}else {
			return d2;
		}
	}
	
	/**
	 * 根据ognl表达式, 获取对应的值
	 * @param ognlExpression 传入null, 则直接获取value值
	 * @return
	 */
	public Object getValue(String ognlExpression) {
		if(value != null && StringUtil.notEmpty(ognlExpression))
			return OgnlHandler.getSingleton().getObjectValue(ognlExpression, value);
		return value;
	}
}
