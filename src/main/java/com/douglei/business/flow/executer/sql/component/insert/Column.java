package com.douglei.business.flow.executer.sql.component.insert;

import com.douglei.business.flow.executer.condition.ConditionValidator;
import com.douglei.business.flow.executer.sql.SqlData;
import com.douglei.business.flow.executer.sql.component.Component;
import com.douglei.tools.StringUtil;

/**
 * 
 * @author DougLei
 */
public class Column extends Component{
	private static final long serialVersionUID = 1194819731180250264L;
	protected String column;
	
	protected Column(ConditionValidator validator) {
		this(validator, null);
	}
	public Column(ConditionValidator validator, String column) {
		super(validator);
		setColumn(column);
	}

	public void setColumn(String column) {
		if(StringUtil.notEmpty(column))
			this.column = column;
	}
	
	@Override
	public void append2SqlData(SqlData sqlData) {
		// TODO Auto-generated method stub
		
	}
}
