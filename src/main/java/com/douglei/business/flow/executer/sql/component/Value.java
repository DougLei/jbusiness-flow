package com.douglei.business.flow.executer.sql.component;

import java.util.Date;

import com.douglei.business.flow.executer.DataType;
import com.douglei.business.flow.executer.ParameterContext;
import com.douglei.business.flow.executer.condition.ConditionValidator;
import com.douglei.business.flow.executer.parameter.DeclaredParameter;
import com.douglei.business.flow.executer.sql.SqlData;
import com.douglei.business.flow.executer.sql.component.insert.Column;
import com.douglei.business.flow.executer.sql.component.select.Select;
import com.douglei.business.flow.resolver.action.impl.sql.op.SqlDefinedParameterContext;
import com.douglei.tools.utils.StringUtil;
import com.douglei.tools.utils.datatype.dateformat.DateFormatUtil;

/**
 * 
 * @author DougLei
 */
public class Value extends Column{
	
	private Object value; 
	private DeclaredParameter parameter;
	private DataType dataType;
	private boolean placeholder;
	private String valuePrefix="";
	private String valueSuffix="";
	private String format;
	
	private Function function;
	private Select[] selects;
	
	public Value(ConditionValidator validator) {
		super(validator);
	}
	
	public void setValue(Object value, Boolean placeholder, String valuePrefix, String valueSuffix, String format) {
		this.dataType = DataType.toValue(value);
		this.placeholder = placeholder == null?true:placeholder;
		setValuePrefixAndSuffix(valuePrefix, valueSuffix, dataType);
		this.format = format;
		if(this.placeholder) {
			this.value = dataType.convert(value);
		}else {
			this.value = value;
		}
	}
	public void setParameter(DeclaredParameter parameter, Boolean placeholder, String valuePrefix, String valueSuffix, String format) {
		this.parameter = parameter;
		this.placeholder = placeholder == null?true:placeholder;
		setValuePrefixAndSuffix(valuePrefix, valueSuffix, SqlDefinedParameterContext.get(parameter.getName()).getDataType());
		this.format = StringUtil.isEmpty(format)?"yyyy-MM-dd":format;
	}
	private void setValuePrefixAndSuffix(String valuePrefix, String valueSuffix, DataType dataType) {
		if(!this.placeholder) {
			this.valuePrefix = valuePrefix==null?(dataType.isNumber()?"":"'"):valuePrefix;
			this.valueSuffix = valueSuffix==null?(dataType.isNumber()?"":"'"):valueSuffix;
		}
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
				sqlData.appendSql(valuePrefix).appendSql((dataType==DataType.DATE && StringUtil.notEmpty(format))?DateFormatUtil.format(DateFormatUtil.parseDate(value), format):value).appendSql(valueSuffix);
			}
		}else if(parameter != null) {
			Object parameterValue = ParameterContext.getValue(parameter);
			if(placeholder) {
				sqlData.appendSql('?');
				sqlData.addParameterValue(parameterValue);
			}else {
				sqlData.appendSql(valuePrefix).appendSql(dataType==DataType.DATE?DateFormatUtil.format((Date)parameterValue, format):parameterValue).appendSql(valueSuffix);
			}
		}else if(function != null) {
			function.append2SqlData(sqlData);
		}else if(selects != null) {
			Component.appendComponents2SqlData("(", ")", selects, sqlData);
		}
	}
}
