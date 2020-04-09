package com.douglei.business.flow.executer.sql.component.select;

import com.douglei.business.flow.executer.sql.SqlData;
import com.douglei.business.flow.executer.sql.component.Component;
import com.douglei.business.flow.executer.sql.component.Table;
import com.douglei.business.flow.executer.sql.component.select.condition.ConditionGroups;
import com.douglei.business.flow.executer.sql.component.select.group.and.order.GroupAndOrders;

/**
 * 
 * @author DougLei
 */
public class Select implements Component{
	private Result[] results;
	private Table table;
	
	private Join[] joins;
	private ConditionGroups whereGroups;
	private GroupAndOrders groupBys;
	private ConditionGroups havingGroups;
	private GroupAndOrders orderBys;

	private UnionType union;
	public Select(byte union) {
		this.union = UnionType.toValue(union);
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
	public void setGroupBys(GroupAndOrders groupBys) {
		this.groupBys = groupBys;
	}
	public void setHavingGroups(ConditionGroups havingGroups) {
		this.havingGroups = havingGroups;
	}
	public void setOrderBys(GroupAndOrders orderBys) {
		this.orderBys = orderBys;
	}

	@Override
	public void append2SqlData(SqlData sqlData) {
		sqlData.appendSql(" SELECT ");
		Component.appendComponents2SqlData(results, sqlData);
		sqlData.appendSql(" FROM ");
		table.append2SqlData(sqlData);
		
		if(joins != null)
			Component.appendComponents2SqlData(joins, sqlData);
		if(whereGroups != null)
			whereGroups.append2SqlData(sqlData);
		if(groupBys != null)
			groupBys.append2SqlData(sqlData);
		if(havingGroups != null)
			havingGroups.append2SqlData(sqlData);
		if(orderBys != null)
			orderBys.append2SqlData(sqlData);
	}

	@Override
	public String linkSymbol() {
		return union.sql();
	}
}


/**
 * 
 * @author DougLei
 */
enum UnionType{
	UNION(" UNION "),
	UNION_ALL(" UNION ALL ");
	
	private String sql;
	private UnionType(String sql) {
		this.sql = sql;
	}

	static UnionType toValue(byte value) {
		if(value == 1)
			return UNION_ALL;
		return UNION;
	}

	public String sql() {
		return sql;
	}
}
