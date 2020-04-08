package com.douglei.business.flow.executer.sql.component.select;

import com.douglei.business.flow.executer.sql.component.Table;
import com.douglei.business.flow.executer.sql.component.select.condition.ConditionGroups;

/**
 * 
 * @author DougLei
 */
public class Join {
	public static final byte INNER_JOIN = 0;
	public static final byte LEFT_JOIN = 1;
	public static final byte RIGHT_JOIN = 2;
	public static final byte FULL_JOIN = 3;
	
	private byte type;
	private Table table;
	private ConditionGroups onGroups;
	
	public Join(byte type, Table table, ConditionGroups onGroups) {
		this.type = type;
		this.table = table;
		this.onGroups = onGroups;
	}
}
