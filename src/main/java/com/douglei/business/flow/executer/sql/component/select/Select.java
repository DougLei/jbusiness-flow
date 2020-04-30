package com.douglei.business.flow.executer.sql.component.select;

import com.douglei.business.flow.executer.condition.ConditionValidator;
import com.douglei.business.flow.executer.sql.SqlData;
import com.douglei.business.flow.executer.sql.component.Component;
import com.douglei.business.flow.executer.sql.component.Table;
import com.douglei.business.flow.executer.sql.component.select.condition.ConditionGroupWrapper;

/**
 * 
 * @author DougLei
 */
public class Select extends Component{
	private Result[] results;
	private Table table;
	
	private Join[] joins;
	private ConditionGroupWrapper whereGroups;
	private GroupBy[] groupBys;
	private ConditionGroupWrapper havingGroups;
	private OrderBy[] orderBys;

	private UnionType union;
	public Select(ConditionValidator validator, byte union) {
		super(validator);
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
	public void setWhereGroups(ConditionGroupWrapper whereGroups) {
		this.whereGroups = whereGroups;
	}
	public void setGroupBys(GroupBy[] groupBys) {
		this.groupBys = groupBys;
	}
	public void setHavingGroups(ConditionGroupWrapper havingGroups) {
		this.havingGroups = havingGroups;
	}
	public void setOrderBys(OrderBy[] orderBys) {
		this.orderBys = orderBys;
	}

	@Override
	public void append2SqlData(SqlData sqlData) {
		sqlData.appendSql(" SELECT ");
		if(results == null) {
			sqlData.appendSql('*');
		}else {
			Component.appendComponents2SqlData(results, sqlData);
		}
		sqlData.appendSql(" FROM ");
		table.append2SqlData(sqlData);
		
		if(joins != null)
			Component.appendComponents2SqlData(joins, sqlData);
		if(whereGroups != null)
			whereGroups.append2SqlData(sqlData);
		if(groupBys != null) {
			sqlData.appendSql(" GROUP BY ");
			Component.appendComponents2SqlData(groupBys, sqlData);
		}
		if(havingGroups != null)
			havingGroups.append2SqlData(sqlData);
		if(orderBys != null) {
			sqlData.appendSql(" ORDER BY ");
			Component.appendComponents2SqlData(orderBys, sqlData);
		}
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
