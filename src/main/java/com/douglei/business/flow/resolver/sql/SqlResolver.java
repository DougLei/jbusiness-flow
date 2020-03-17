package com.douglei.business.flow.resolver.sql;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.executer.LogicalOP;
import com.douglei.business.flow.executer.Parameter;
import com.douglei.business.flow.executer.sql.Sql;
import com.douglei.business.flow.executer.sql.component.Function;
import com.douglei.business.flow.executer.sql.component.Table;
import com.douglei.business.flow.executer.sql.component.Value;
import com.douglei.business.flow.executer.sql.component.select.CompareType;
import com.douglei.business.flow.executer.sql.component.select.Condition;
import com.douglei.business.flow.executer.sql.component.select.ConditionGroup;
import com.douglei.business.flow.executer.sql.component.select.GroupAndOrder;
import com.douglei.business.flow.executer.sql.component.select.Join;
import com.douglei.business.flow.executer.sql.component.select.Result;
import com.douglei.business.flow.executer.sql.component.select.Select;
import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public abstract class SqlResolver {
	private static final Select[] EMPTY_SELECT = new Select[0];
	private static final Join[] EMPTY_JOIN = new Join[0];;
	private static final ConditionGroup[] EMPTY_CONDITION_GROUP = new ConditionGroup[0];;
	private static final Condition[] EMPTY_CONDITION = new Condition[0];;
	private static final GroupAndOrder[] EMPTY_GROUP_AND_ORDER = new GroupAndOrder[0];
	
	
	/**
	 * 获取类型
	 * @return
	 */
	public abstract byte getType();

	/**
	 * 解析sql
	 * @param name
	 * @param description
	 * @param parameters
	 * @param content
	 * @return
	 */
	public abstract Sql parse(String name, String description, Parameter[] parameters, JSONObject content);
	
	
	// 解析Function
	private Function parseFunction(JSONObject functionJSON) {
		Function function = new Function(functionJSON.getString("name"));
		
		JSONArray array = functionJSON.getJSONArray("values");
		byte size = array==null?0:(byte)array.size();
		if(size>0) {
			Value[] values = new Value[size];
			for(byte i=0;i<size;i++) {
				values[i] = parseValue(array.getJSONObject(i));
			}
			function.setValues(values);
		}
		return function;
	}
	
	
	// 给子类提供解析必要sql组件的方法
	// 解析Table
	protected Table parseTable(JSONObject tableJSON) {
		Table table = new Table(tableJSON.getString("alias"));
		
		Object object;
		if(StringUtil.notEmpty(object = tableJSON.getString("name"))) {
			table.setName(object.toString());
		}else if(StringUtil.notEmpty(object = tableJSON.getString("paramName"))) {
			table.setParamName(object.toString());
		}else if((object = tableJSON.getJSONObject("function")) != null) {
			table.setFunction(parseFunction((JSONObject)object));
		}else if((object = tableJSON.getJSONArray("selects")) != null) {
			table.setSelects(parseSelects((JSONArray)object));
		}
		return table;
	}
	
	// 解析Value
	protected Value parseValue(JSONObject valueJSON) {
		Value value = new Value();
		
		Object object;
		if(StringUtil.notEmpty(object = valueJSON.getString("column"))) {
			value.setColumn(object.toString());
		}else if((object = valueJSON.get("value")) != null) {
			value.setValue(object, valueJSON.getBoolean("placeholder"), valueJSON.getByteValue("package"));
		}else if(StringUtil.notEmpty(object = valueJSON.getString("paramName"))) {
			value.setParamName(object.toString(), valueJSON.getBoolean("placeholder"), valueJSON.getByteValue("package"));
		}else if((object = valueJSON.getJSONObject("function")) != null) {
			value.setFunction(parseFunction((JSONObject)object));
		}else if((object = valueJSON.getJSONArray("selects")) != null) {
			value.setSelects(parseSelects((JSONArray)object));
		}
		return value;
	}
	
	// ----------------------------------------------------------------------------------------------------
	// 解析selects, 因为都是数组形式配置, 所以不开放解析单个select的方法
	protected Select[] parseSelects(JSONArray selectARRAY) {
		byte size = selectARRAY ==null?0:(byte) selectARRAY.size();
		if(size == 0) {
			return EMPTY_SELECT;
		}
		Select[] selects = new Select[size];
		for(byte i=0;i<size;i++) {
			selects[i] = parseSelect(selectARRAY.getJSONObject(i));
		}
		return selects;
	}
	// 解析select
	private Select parseSelect(JSONObject selectJSON) {
		Select select = new Select(selectJSON.getByteValue("union"));
		select.setResults(parseResults(selectJSON.getJSONArray("results")));
		select.setTable(parseTable(selectJSON.getJSONObject("table")));
		select.setJoins(parseJoins(selectJSON.getJSONArray("joins")));
		select.setWhereGroups(parseConditionGroups(selectJSON.getJSONArray("whereGroups")));
		select.setGroupBys(parseGOs(selectJSON.getJSONArray("groupBys")));
		select.setHavings(parseConditionGroups(selectJSON.getJSONArray("havingGroups")));
		select.setOrderBys(parseGOs(selectJSON.getJSONArray("orderBys")));
		return select;
	}
	// 解析result
	private Result[] parseResults(JSONArray array) {
		JSONObject resultJSON;
		Result[] results = new Result[array.size()];
		for(short i=0;i<array.size();i++) {
			resultJSON = array.getJSONObject(i);
			results[i] = new Result(resultJSON.getString("alias"), parseValue(resultJSON));
		}
		return results;
	}
	// 解析join
	private Join[] parseJoins(JSONArray array) {
		byte size = array==null?0:(byte)array.size();
		if(size == 0) {
			return EMPTY_JOIN;
		}
		Join[] joins = new Join[size];
		JSONObject json;
		for(byte i=0;i<size;i++) {
			json = array.getJSONObject(i);
			joins[i] = new Join(json.getByteValue("type"), parseTable(json.getJSONObject("table")), parseConditionGroups(json.getJSONArray("onGroups")));
		}
		return joins;
	}
	// 解析条件组, 包括where, join中的on, having
	protected ConditionGroup[] parseConditionGroups(JSONArray array) {
		byte size = array==null?0:(byte)array.size();
		if(size == 0) {
			return EMPTY_CONDITION_GROUP;
		}
		ConditionGroup[] conditionGroups = new ConditionGroup[size];
		JSONObject json;
		for(byte i=0;i<size;i++) {
			json = array.getJSONObject(i);
			conditionGroups[i] = new ConditionGroup(parseConditions(json.getJSONArray("conditions")), LogicalOP.toValue(json.getByteValue("op")));
		}
		return conditionGroups;
	}
	// 解析具体的conditions
	private Condition[] parseConditions(JSONArray array) {
		byte size = array==null?0:(byte)array.size();
		if(size == 0) {
			return EMPTY_CONDITION;
		}
		Condition[] conditions = new Condition[size];
		JSONObject json;
		for(byte i=0;i<size;i++) {
			json = array.getJSONObject(i);
			conditions[i] = new Condition(parseValue(json.getJSONObject("left")), CompareType.toValue(json.getString("cop")), LogicalOP.toValue(json.getByteValue("op")));
			if(conditions[i].getCop() != CompareType.NULL && conditions[i].getCop() != CompareType.NNULL) {
				conditions[i].setRights(parseRights(json.getJSONArray("rights")));
			}
		}
		return conditions;
	}
	// 解析condition中right部分
	private Value[] parseRights(JSONArray array) {
		Value[] rights = new Value[array.size()];
		for(byte i=0;i<array.size();i++) {
			rights[i] = parseValue(array.getJSONObject(i));
		}
		return rights;
	}

	// 解析group by和order by
	private GroupAndOrder[] parseGOs(JSONArray array) {
		byte size = array==null?0:(byte)array.size();
		if(size == 0) {
			return EMPTY_GROUP_AND_ORDER;
		}
		GroupAndOrder[] gos = new GroupAndOrder[size];
		JSONObject goJSON;
		String column;
		for(byte i=0;i<size;i++) {
			goJSON = array.getJSONObject(i);
			gos[i] = new GroupAndOrder(goJSON.getByteValue("sort"));
			if(StringUtil.notEmpty(column = goJSON.getString("column"))) {
				gos[i].setColumn(column);
			}else {
				gos[i].setFunction(parseFunction(goJSON.getJSONObject("function")));
			}
		}
		return gos;
	}
}
