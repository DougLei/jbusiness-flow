package com.douglei.business.flow.executer.sql.component.insert;

import com.douglei.business.flow.executer.sql.SqlData;
import com.douglei.business.flow.executer.sql.component.Component;
import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public class Column extends Component{
	protected String column;
	
	protected Column() {}
	public Column(String column) {
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
