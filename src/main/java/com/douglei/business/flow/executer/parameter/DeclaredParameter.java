package com.douglei.business.flow.executer.parameter;

import com.douglei.business.flow.executer.DataType;

/**
 * 声明的参数(形参)
 * @author DougLei
 */
public class DeclaredParameter extends ResultParameter {
	protected boolean required;
	
	public DeclaredParameter(String name, Scope scope, DataType dataType, Object defaultValue, boolean required) {
		super(name, scope, dataType);
		if(defaultValue != null) {
			if(!dataType.matching(defaultValue))
				throw new IllegalArgumentException("在"+scope.getDescription()+"范围中, 名为"+name+"的参数, 其默认值("+defaultValue+")的数据类型与配置的参数数据类型("+dataType.name()+")不匹配");
			super.defaultValue = dataType.convert(defaultValue);
		}
		this.required = required;
	}
	
	@Override
	public ActualParameter toActualParameter(Object value) {
		ActualParameter ap = new ActualParameter(name, scope, dataType);
		ap.setValue(required, super.defaultValue, value);
		return ap;
	}
}