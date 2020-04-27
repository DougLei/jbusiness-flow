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
		super(name, scope, null);
		this.dataType = dataType;
	}
	
	public final DataType getDataType() {
		return dataType;
	}
	
	/**
	 * 修改数据类型
	 * @param dataType
	 */
	public void updateDataType(DataType dataType) {
		this.dataType = dataType;
	}
	
	/**
	 * 根据value, 转换为实参
	 * @param value
	 * @return
	 */
	public ActualParameter toActualParameter(Object value) {
		DataType valueDataType = dataType;
		if(valueDataType == null) 
			valueDataType = DataType.toValue(value);
		
		ActualParameter ap = new ActualParameter(name, scope, valueDataType);
		ap.setValue(false, null, value);
		return ap;
	}
}
