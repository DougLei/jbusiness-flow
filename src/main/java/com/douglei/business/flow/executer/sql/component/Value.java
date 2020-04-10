package com.douglei.business.flow.executer.sql.component;

import java.util.Date;

import com.douglei.business.flow.executer.DataType;
import com.douglei.business.flow.executer.ParameterContext;
import com.douglei.business.flow.executer.parameter.Parameter;
import com.douglei.business.flow.executer.sql.SqlData;
import com.douglei.business.flow.executer.sql.component.select.Select;
import com.douglei.business.flow.resolver.action.impl.sql.SqlDefinedParameterContext;
import com.douglei.tools.utils.datatype.dateformat.DateFormatUtil;

/**
 * 
 * @author DougLei
 */
public class Value implements Component{
	private String column;
	
	private Object value; 
	private Parameter parameter;
	private DataType dataType;
	private boolean placeholder;
	private String valuePrefix="";
	private String valueSuffix="";
	private String format;
	
	private Function function;
	private Select[] selects;
	
	public void setColumn(String column) {
		this.column = column;
	}
	public void setValue(Object value, Boolean placeholder, String valuePrefix, String valueSuffix, String format) {
		this.dataType = DataType.toValue(value);
		this.placeholder = placeholder == null?true:placeholder;
		setValuePrefixAndSuffix(valuePrefix, valueSuffix, dataType);
		this.format = format;
		this.value = getFinalValue(dataType.convert(value));
	}
	public void setParameter(Parameter parameter, Boolean placeholder, String valuePrefix, String valueSuffix, String format) {
		this.parameter = parameter;
		this.placeholder = placeholder == null?true:placeholder;
		setValuePrefixAndSuffix(valuePrefix, valueSuffix, SqlDefinedParameterContext.get(parameter.getName()).getDataType());
		this.format = format;
	}
	private void setValuePrefixAndSuffix(String valuePrefix, String valueSuffix, DataType dataType) {
		if(!this.placeholder) {
			this.valuePrefix = valuePrefix==null?(dataType.isNumber()?"":"'"):valuePrefix;
			this.valueSuffix = valueSuffix==null?(dataType.isNumber()?"":"'"):valueSuffix;
		}
	}
	// 获取最终的value值
	private Object getFinalValue(Object value) {
		if(!placeholder && dataType == DataType.DATE) {
			return DateFormatUtil.format((Date)value, format);
		}
		return value;
	}
	public void setFunction(Function function) {
		this.function = function;
	}
	public void setSelects(Select[] selects) {
		this.selects = selects;
	}
	
	@Override
	public void append2SqlData(SqlData sqlData) {
		if(column != null) {
			sqlData.appendSql(column);
		}else if(value != null) {
			if(placeholder) {
				sqlData.appendSql('?');
				sqlData.addParameterValue(value);
			}else {
				sqlData.appendSql(valuePrefix).appendSql(value).appendSql(valueSuffix);
			}
		}else if(parameter != null) {
			Object parameterValue = ParameterContext.getValue(parameter);
			if(placeholder) {
				sqlData.appendSql('?');
				sqlData.addParameterValue(parameterValue);
			}else {
				sqlData.appendSql(valuePrefix).appendSql(getFinalValue(parameterValue)).appendSql(valueSuffix);
			}
		}else if(function != null) {
			function.append2SqlData(sqlData);
		}else if(selects != null) {
			sqlData.appendSql('(');
			Component.appendComponents2SqlData(selects, sqlData);
			sqlData.appendSql(')');
		}
	}
}
