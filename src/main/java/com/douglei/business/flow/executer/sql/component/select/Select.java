package com.douglei.business.flow.executer.sql.component.select;

import com.douglei.business.flow.executer.sql.SqlData;
import com.douglei.business.flow.executer.sql.component.Component;
import com.douglei.business.flow.executer.sql.component.Table;
import com.douglei.business.flow.executer.sql.component.select.condition.ConditionGroups;

/**
 * 
 * @author DougLei
 */
public class Select implements Component{
	private static final byte UNION = 0;
	private static final String UNION_ = " UNION ";
	private static final byte UNION_ALL = 1;
	private static final String UNION_ALL_ = " UNION ALL ";
	
	private Result[] results;
	private Table table;
	private Join[] joins;
	private ConditionGroups whereGroups;
	private GroupAndOrder[] groupBys;
	private ConditionGroups havingGroups;
	private GroupAndOrder[] orderBys;
	private byte union;

	public Select(byte union) {
		this.union = union;
	}

	public void setResults(Result[] results) {
		this.results = results;
	}
	public void setTable(Table table) {
		this.table = table;
	}
	public void setJoins(Join[] joins) {
		this.joins = joins;
	}
	public void setWhereGroups(ConditionGroups whereGroups) {
		this.whereGroups = whereGroups;
	}
	public void setGroupBys(GroupAndOrder[] groupBys) {
		this.groupBys = groupBys;
	}
	public void setHavingGroups(ConditionGroups havingGroups) {
		this.havingGroups = havingGroups;
	}
	public void setOrderBys(GroupAndOrder[] orderBys) {
		this.orderBys = orderBys;
	}

	@Override
	public void append2SqlData(SqlData sqlData) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String linkSymbol() {
		if(union == UNION)
			return UNION_;
		if(union == UNION_ALL)
			return UNION_ALL_;
		return Component.super.linkSymbol();
	}
}
