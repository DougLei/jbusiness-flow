package com.douglei.business.flow.executer.sql.component.select.condition;

import com.douglei.business.flow.executer.action.impl.data.op.compare.CompareTypeMatchingException;
import com.douglei.business.flow.executer.sql.SqlData;
import com.douglei.business.flow.executer.sql.component.Component;
import com.douglei.business.flow.executer.sql.component.Value;
import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public enum CompareType {
	EQ("NE", "="){
		@Override
		public CompareType correct(int rightValueCount) {
			if(rightValueCount == 1)
				return this;
			return IN;
		}
	},
	NE("EQ", "!="){
		@Override
		public CompareType correct(int rightValueCount) {
			if(rightValueCount == 1)
				return this;
			return NIN;
		}
	},
	GT("LT", ">"),
	GE("LE", ">="),
	LT("GT", "<"),
	LE("GE", "<="),
	BTN("NBTN"){
		@Override
		public void append2SqlData(Value left, Value[] rights, SqlData sqlData) {
			sqlData.appendSql(" BETWEEN ");
			left.append2SqlData(sqlData);
			sqlData.appendSql(" AND ");
			rights[0].append2SqlData(sqlData);
		}
	},
	NBTN("BTN"){
		@Override
		public void append2SqlData(Value left, Value[] rights, SqlData sqlData) {
			sqlData.appendSql(" NOT");
			BTN.append2SqlData(left, rights, sqlData);
		}
	},
	IN("NIN", " IN ", true){
		@Override
		public CompareType correct(int rightValueCount) {
			if(rightValueCount > 1)
				return this;
			return EQ;
		}
	},
	NIN("IN", " NOT IN ", true){
		@Override
		public CompareType correct(int rightValueCount) {
			if(rightValueCount > 1)
				return this;
			return NE;
		}
	},
	LIKE("NLIKE", " LIKE "),
	NLIKE("LIKE", " NOT LIKE "),
	NULL("NNULL", " IS NULL "),
	NNULL("NULL", " IS NOT NULL ");
	
	private String inversion;
	private String linkSymbol;
	private boolean multiRightValues;
	private CompareType(String inversion) {
		this.inversion = inversion;
	}
	private CompareType(String inversion, String linkSymbol) {
		this(inversion);
		this.linkSymbol = linkSymbol;
	}
	private CompareType(String inversion, String linkSymbol, boolean multiRightValues) {
		this(inversion, linkSymbol);
		this.multiRightValues = multiRightValues;
	}
	
	public static CompareType toValue(String value) {
		if(StringUtil.notEmpty(value)) {
			value = value.trim().toUpperCase();
			
			boolean isInversion = false;
			if(isInversion = value.charAt(0) == '!') {
				value = value.substring(1);
			}
			for(CompareType ct : CompareType.values()) {
				if(ct.name().equals(value)) {
					if(isInversion) {
						return CompareType.toValue(ct.inversion);
					}
					return ct;
				}
			}
		}
		throw new CompareTypeMatchingException(value);
	}
	
	/**
	 * 将left和right的数据追加到SqlData中
	 * @param left
	 * @param rights
	 * @param sqlData
	 */
	public void append2SqlData(Value left, Value[] rights, SqlData sqlData) {
		left.append2SqlData(sqlData);
		sqlData.appendSql(linkSymbol);
		if(rights != null) {
			if(multiRightValues) {
				sqlData.appendSql('(');
				Component.appendComponents2SqlData(rights, sqlData);
				sqlData.appendSql(')');
			}else {
				rights[0].append2SqlData(sqlData);
			}
		}
	}
	
	/**
	 * 根据右边的参数的数量, 修正CompareType, 
	 * 例如CompareType为eq, rights有多个, 将CompareType应为in, 而不能再是eq
	 * 当然, 如果CompareType和右边的参数数量匹配, 则不用修正
	 * @param rightValueCount
	 * @return 修正后的CompareType
	 */
	public CompareType correct(int rightValueCount) {
		return this;
	}
}
