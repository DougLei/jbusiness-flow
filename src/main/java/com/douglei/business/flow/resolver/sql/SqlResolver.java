package com.douglei.business.flow.resolver.sql;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.executer.LogicalOP;
import com.douglei.business.flow.executer.parameter.Parameter;
import com.douglei.business.flow.executer.sql.Sql;
import com.douglei.business.flow.executer.sql.component.Function;
import com.douglei.business.flow.executer.sql.component.Table;
import com.douglei.business.flow.executer.sql.component.Value;
import com.douglei.business.flow.executer.sql.component.select.Join;
import com.douglei.business.flow.executer.sql.component.select.Result;
import com.douglei.business.flow.executer.sql.component.select.Select;
import com.douglei.business.flow.executer.sql.component.select.condition.CompareType;
import com.douglei.business.flow.executer.sql.component.select.condition.Condition;
import com.douglei.business.flow.executer.sql.component.select.condition.ConditionGroup;
import com.douglei.business.flow.executer.sql.component.select.condition.ConditionGroups;
import com.douglei.business.flow.executer.sql.component.select.condition.ConditionType;
import com.douglei.business.flow.executer.sql.component.select.group.and.order.GroupAndOrderType;
import com.douglei.business.flow.executer.sql.component.select.group.and.order.GroupAndOrders;
import com.douglei.business.flow.resolver.action.impl.sql.SqlDefinedParameterContext;
import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public abstract class SqlResolver {

	/**
	 * 获取类型
	 * @return
	 */
	public abstract byte getType();

	/**
	 * 解析sql
	 * @param name
	 * @param parameters
	 * @param content
	 * @return
	 */
	public abstract Sql parse(String name, Parameter[] parameters, JSONObject content);
	
	
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
			table.setParameter(SqlDefinedParameterContext.get(object.toString()));
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
			value.setValue(object, valueJSON.getBoolean("placeholder"), valueJSON.getString("valuePrefix"), valueJSON.getString("valueSuffix"), valueJSON.getString("format"));
		}else if(StringUtil.notEmpty(object = valueJSON.getString("paramName"))) {
			value.setParameter(SqlDefinedParameterContext.get(object.toString()), valueJSON.getBoolean("placeholder"), valueJSON.getString("valuePrefix"), valueJSON.getString("valueSuffix"), valueJSON.getString("format"));
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
			return null;
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
		select.setWhereGroups(parseConditionGroups(ConditionType.WHERE, selectJSON));
		select.setGroupBys(parseGroupAndOrders(GroupAndOrderType.GROUP_BY, selectJSON));
		select.setHavingGroups(parseConditionGroups(ConditionType.HAVING, selectJSON));
		select.setOrderBys(parseGroupAndOrders(GroupAndOrderType.ORDER_BY, selectJSON));
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
			return null;
		}
		Join[] joins = new Join[size];
		JSONObject json;
		for(byte i=0;i<size;i++) {
			json = array.getJSONObject(i);
			joins[i] = new Join(json.getByteValue("type"), parseTable(json.getJSONObject("table")), parseConditionGroups(ConditionType.ON, json));
		}
		return joins;
	}
	
	// 解析条件组, 包括where, join中的on, having
	protected ConditionGroups parseConditionGroups(ConditionType type, JSONObject content) {
		ConditionGroup[] array = parseConditionGroups_(content.getJSONArray(type.getJsonKey()));
		if(array == null) {
			return null;
		}
		return new ConditionGroups(type.getPrefixSql(), array);
	}
	private ConditionGroup[] parseConditionGroups_(JSONArray array) {
		byte size = array==null?0:(byte)array.size();
		if(size == 0) {
			return null;
		}
		ConditionGroup[] conditionGroups = new ConditionGroup[size];
		JSONObject json;
		for(byte i=0;i<size;i++) {
			json = array.getJSONObject(i);
			conditionGroups[i] = new ConditionGroup(parseConditionGroups_(json.getJSONArray("conditionGroups")), parseConditions(json.getJSONArray("conditions")), LogicalOP.toValue(json.getByteValue("cgcop")), LogicalOP.toValue(json.getByteValue("op")));
		}
		return conditionGroups;
	}
	// 解析具体的conditions
	private Condition[] parseConditions(JSONArray array) {
		byte size = array==null?0:(byte)array.size();
		if(size == 0) {
			return null;
		}
		Condition[] conditions = new Condition[size];
		JSONObject json;
		for(byte i=0;i<size;i++) {
			json = array.getJSONObject(i);
			conditions[i] = new Condition(parseValue(json.getJSONObject("left")), CompareType.toValue(json.getString("cop")), LogicalOP.toValue(json.getByteValue("op")));
			if(conditions[i].opIsNULL()) {
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
	private GroupAndOrders parseGroupAndOrders(GroupAndOrderType type, JSONObject selectJSON) {
		JSONArray array = selectJSON.getJSONArray(type.getJsonKey());
		byte size = array==null?0:(byte)array.size();
		if(size == 0) {
			return null;
		}
		GroupAndOrders groupAndOrders = new GroupAndOrders(type.getPrefixSql(), size);
		
		JSONObject goJSON;
		String column;
		for(byte i=0;i<size;i++) {
			goJSON = array.getJSONObject(i);
			groupAndOrders.initialGroupAndOrder(i, goJSON.getByte("sort"));
			if(StringUtil.notEmpty(column = goJSON.getString("column"))) {
				groupAndOrders.setColumn(i, column);
			}else {
				groupAndOrders.setFunction(i, parseFunction(goJSON.getJSONObject("function")));
			}
		}
		return groupAndOrders;
	}
}
