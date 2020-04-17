package com.douglei.business.flow.executer.parameter;

import com.douglei.business.flow.executer.DataType;

/**
 * result, 结果参数
 * @author DougLei
 */
public class ResultParameter extends Parameter {
	protected DataType dataType;
	
	public ResultParameter() {}
	public ResultParameter(String name, Scope scope, DataType dataType) {
		super.name = name;
		super.scope = scope;
		this.dataType = dataType;
	}
	
	public final DataType getDataType() {
		return dataType;
	}
}
