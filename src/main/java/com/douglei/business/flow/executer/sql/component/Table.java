package com.douglei.business.flow.executer.sql.component;

import com.douglei.business.flow.executer.ParameterContext;
import com.douglei.business.flow.executer.condition.ConditionValidator;
import com.douglei.business.flow.executer.parameter.DeclaredParameter;
import com.douglei.business.flow.executer.sql.SqlData;
import com.douglei.business.flow.executer.sql.component.select.Select;
import com.douglei.tools.StringUtil;

/**
 * 
 * @author DougLei
 */
public class Table extends Component{
	private static final long serialVersionUID = -2246294276519782516L;
	private String alias;
	private String name;
	
	private DeclaredParameter parameter;
	private Function function;
	private Select[] selects;
	
	public Table(ConditionValidator validator, String alias) {
		super(validator);
		if(StringUtil.unEmpty(alias)) {
			this.alias = alias;
		}
	}

	public void setName(String name) {
		this.name = name;
	}
	public void setParameter(DeclaredParameter parameter) {
		this.parameter = parameter;
	}
	public void setFunction(Function function) {
		this.function = function;
	}
	public void setSelects(Select[] selects) {
		this.selects = selects;
	}

	@Override
	public void append2SqlData(SqlData sqlData) {
		if(name != null) {
			sqlData.appendSql(name);
		}else if(parameter != null) {
			sqlData.appendSql(ParameterContext.getValue(parameter));
		}else if(function != null) {
			function.append2SqlData(sqlData);
		}else if(selects != null) {
			Component.appendComponents2SqlData("(", ")", selects, sqlData);
		}
		
		if(alias != null) {
			sqlData.appendSql(" AS ").appendSql(alias);
		}
	}
}
