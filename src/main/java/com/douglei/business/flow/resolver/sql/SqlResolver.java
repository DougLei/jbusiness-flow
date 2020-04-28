package com.douglei.business.flow.resolver.sql;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.executer.LogicalOP;
import com.douglei.business.flow.executer.parameter.DeclaredParameter;
import com.douglei.business.flow.executer.sql.Sql;
import com.douglei.business.flow.executer.sql.component.Function;
import com.douglei.business.flow.executer.sql.component.Table;
import com.douglei.business.flow.executer.sql.component.Value;
import com.douglei.business.flow.executer.sql.component.insert.Column;
import com.douglei.business.flow.executer.sql.component.select.GroupBy;
import com.douglei.business.flow.executer.sql.component.select.Join;
import com.douglei.business.flow.executer.sql.component.select.OrderBy;
import com.douglei.business.flow.executer.sql.component.select.Result;
import com.douglei.business.flow.executer.sql.component.select.Select;
import com.douglei.business.flow.executer.sql.component.select.condition.CompareType;
import com.douglei.business.flow.executer.sql.component.select.condition.Condition;
import com.douglei.business.flow.executer.sql.component.select.condition.ConditionGroup;
import com.douglei.business.flow.executer.sql.component.select.condition.ConditionGroups;
import com.douglei.business.flow.executer.sql.component.select.condition.ConditionType;
import com.douglei.business.flow.resolver.action.impl.sql.op.SqlDefinedParameterContext;
import com.douglei.tools.utils.CollectionUtil;
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
	public abstract String getType();

	/**
	 * 解析sql
	 * @param name
	 * @param parameters
	 * @param sqlJSON
	 * @return
	 */
	public abstract Sql parse(String name, DeclaredParameter[] parameters, JSONObject sqlJSON);
	
	
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
	
	// 解析column数组
	protected Column[] parseColumns(JSONArray array) {
		if(CollectionUtil.isEmpty(array))
			return null;
		
		Column[] columns = new Column[array.size()];
		for(int i=0;i<array.size();i++) {
			columns[i] = new Column(array.getJSONObject(i).getString("column"));
		}
		return columns;
	}
	
	// 解析Value
	protected Value parseValue(JSONObject valueJSON) {
		Value value = new Value();
		setValue(valueJSON, value);
		return value;
	}
	// 给value中设置值
	private void setValue(JSONObject valueJSON, Value value) {
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
		select.setGroupBys(parseGroupBys(selectJSON.getJSONArray("groupBys")));
		select.setHavingGroups(parseConditionGroups(ConditionType.HAVING, selectJSON));
		select.setOrderBys(parseOrderBys(selectJSON.getJSONArray("orderBys")));
		return select;
	}
	// 解析result
	private Result[] parseResults(JSONArray array) {
		JSONObject resultJSON;
		Result[] results = new Result[array.size()];
		for(short i=0;i<array.size();i++) {
			resultJSON = array.getJSONObject(i);
			results[i] = new Result(resultJSON.getString("alias"));
			setValue(resultJSON, results[i]);
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
			if(conditions[i].unNullCompareOP()) {
				conditions[i].setRights(parseRights(json.getJSONArray("rights")));
			}
		}
		return conditions;
	}
	// 解析condition中right部分
	private Value[] parseRights(JSONArray array) {
		if(array == null || array.size() == 0)
			throw new NullPointerException("条件右边的值不能为空");
		
		Value[] rights = new Value[array.size()];
		for(int i=0;i<array.size();i++) {
			rights[i] = parseValue(array.getJSONObject(i));
		}
		return rights;
	}

	// 解析group by
	private GroupBy[] parseGroupBys(JSONArray array) {
		if(CollectionUtil.isEmpty(array))
			return null;
		
		GroupBy[] groupBys = new GroupBy[array.size()];
		for(int i=0;i<array.size();i++) {
			groupBys[i] = new GroupBy();
			setGroupByInfo(groupBys[i], array.getJSONObject(i));
		}
		return groupBys;
	}
	
	// 解析order by
	private OrderBy[] parseOrderBys(JSONArray array) {
		if(CollectionUtil.isEmpty(array))
			return null;
		
		JSONObject json;
		OrderBy[] orderBys = new OrderBy[array.size()];
		for(int i=0;i<array.size();i++) {
			json = array.getJSONObject(i);
			orderBys[i] = new OrderBy(json.getByteValue("sort"));
			setGroupByInfo(orderBys[i], json);
		}
		return orderBys;
	}
	
	// 设置group by的信息
	private void setGroupByInfo(GroupBy groupBy, JSONObject json) {
		groupBy.setColumn(json.getString("column"));
		if(groupBy.columnIsEmpty())
			groupBy.setFunction(parseFunction(json.getJSONObject("function")));
	}
}
