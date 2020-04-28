package com.douglei.business.flow.executer.sql.component;

import com.douglei.business.flow.executer.ParameterContext;
import com.douglei.business.flow.executer.parameter.DeclaredParameter;
import com.douglei.business.flow.executer.sql.SqlData;
import com.douglei.business.flow.executer.sql.component.select.Select;
import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public class Table extends Component{
	private String alias;
	private String name;
	
	private DeclaredParameter parameter;
	private Function function;
	private Select[] selects;
	
	public Table(String alias) {
		if(StringUtil.notEmpty(alias)) {
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
			sqlData.appendSql('(');
			Component.appendComponents2SqlData(selects, sqlData);
			sqlData.appendSql(')');
		}
		
		if(alias != null) {
			sqlData.appendSql(" AS ").appendSql(alias);
		}
	}
}
