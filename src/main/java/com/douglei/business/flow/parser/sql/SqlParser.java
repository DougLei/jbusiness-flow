package com.douglei.business.flow.parser.sql;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.executer.LogicalOP;
import com.douglei.business.flow.executer.condition.ConditionValidator;
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
import com.douglei.business.flow.executer.sql.component.select.condition.ConditionGroupWrapper;
import com.douglei.business.flow.executer.sql.component.select.condition.ConditionType;
import com.douglei.business.flow.parser.ReferenceParser;
import com.douglei.business.flow.parser.action.impl.sql.op.SqlDefinedParameterContext;
import com.douglei.business.flow.parser.condition.ConditionParser;
import com.douglei.tools.StringUtil;

/**
 * 
 * @author DougLei
 */
public abstract class SqlParser {

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
	 * @param referenceResolver
	 * @return
	 */
	public abstract Sql parse(String name, DeclaredParameter[] parameters, JSONObject sqlJSON, ReferenceParser referenceResolver);
	
	/**
	 * 解析条件验证器
	 * @param json
	 * @param referenceResolver
	 * @return
	 */
	protected final ConditionValidator parseConditionValidator(JSONObject json, ReferenceParser referenceResolver) {
		if(json == null)
			return ConditionValidator.defaultValidator();
		return ConditionParser.parse(json.getJSONArray("dynamic"), referenceResolver);
	}
	
	// 解析Function
	private Function parseFunction(JSONObject json, ReferenceParser referenceResolver) {
		Function function = new Function(ConditionParser.parse(null, referenceResolver), json.getString("name"));
		
		JSONArray array = json.getJSONArray("values");
		if(array != null && !array.isEmpty()) {
			Value[] values = new Value[array.size()];
			for(int i=0;i<array.size();i++) {
				values[i] = parseValue(array.getJSONObject(i), referenceResolver);
			}
			function.setValues(values);
		}
		return function;
	}
	
	
	// 给子类提供解析必要sql组件的方法
	// 解析Table
	protected Table parseTable(JSONObject json, ReferenceParser referenceResolver) {
		Table table = new Table(ConditionParser.parse(null, referenceResolver), json.getString("alias"));
		
		Object object;
		if(StringUtil.unEmpty(object = json.getString("name"))) {
			table.setName(object.toString());
		}else if(StringUtil.unEmpty(object = json.getString("paramName"))) {
			table.setParameter(SqlDefinedParameterContext.get(object.toString()));
		}else if((object = json.getJSONObject("function")) != null) {
			table.setFunction(parseFunction((JSONObject)object, referenceResolver));
		}else if((object = json.getJSONArray("selects")) != null) {
			table.setSelects(parseSelects((JSONArray)object, referenceResolver));
		}
		return table;
	}
	
	// 解析column数组
	protected Column[] parseColumns(JSONArray array, ReferenceParser referenceResolver) {
		if(array == null || array.isEmpty())
			return null;
		
		Column[] columns = new Column[array.size()];
		JSONObject json;
		for(int i=0;i<array.size();i++) {
			json = array.getJSONObject(i);
			columns[i] = new Column(parseConditionValidator(json, referenceResolver), json.getString("column"));
		}
		return columns;
	}
	
	// 解析Value
	protected Value parseValue(JSONObject json, ReferenceParser referenceResolver) {
		Value value = new Value(parseConditionValidator(json, referenceResolver));
		setValue(json, value, referenceResolver);
		return value;
	}
	// 给value中设置值
	private void setValue(JSONObject json, Value value, ReferenceParser referenceResolver) {
		Object object;
		if(StringUtil.unEmpty(object = json.getString("column"))) {
			value.setColumn(object.toString());
		}else if((object = json.get("value")) != null) {
			value.setValue(object, json.getBoolean("placeholder"), json.getString("valuePrefix"), json.getString("valueSuffix"), json.getString("format"));
		}else if(StringUtil.unEmpty(object = json.getString("paramName"))) {
			value.setParameter(SqlDefinedParameterContext.get(object.toString()), json.getBoolean("placeholder"), json.getString("valuePrefix"), json.getString("valueSuffix"), json.getString("format"));
		}else if((object = json.getJSONObject("function")) != null) {
			value.setFunction(parseFunction((JSONObject)object, referenceResolver));
		}else if((object = json.getJSONArray("selects")) != null) {
			value.setSelects(parseSelects((JSONArray)object, referenceResolver));
		}
	}
	
	// ----------------------------------------------------------------------------------------------------
	// 解析selects, 因为都是数组形式配置, 所以不开放解析单个select的方法
	protected Select[] parseSelects(JSONArray array, ReferenceParser referenceResolver) {
		Select[] selects = new Select[array.size()];
		for(int i=0;i<array.size();i++) {
			selects[i] = parseSelect(array.getJSONObject(i), referenceResolver);
		}
		return selects;
	}
	// 解析select
	private Select parseSelect(JSONObject json, ReferenceParser referenceResolver) {
		Select select = new Select(parseConditionValidator(json, referenceResolver), json.getByteValue("union"));
		select.setResults(parseResults(json.getJSONArray("results"), referenceResolver));
		select.setTable(parseTable(json.getJSONObject("table"), referenceResolver));
		select.setJoins(parseJoins(json.getJSONArray("joins"), referenceResolver));
		select.setWhereGroups(parseConditionGroupWrapper(ConditionType.WHERE, json, referenceResolver));
		select.setGroupBys(parseGroupBys(json.getJSONArray("groupBys"), referenceResolver));
		select.setHavingGroups(parseConditionGroupWrapper(ConditionType.HAVING, json, referenceResolver));
		select.setOrderBys(parseOrderBys(json.getJSONArray("orderBys"), referenceResolver));
		return select;
	}
	// 解析result
	private Result[] parseResults(JSONArray array, ReferenceParser referenceResolver) {
		if(array == null || array.isEmpty())
			return null;
		JSONObject json;
		Result[] results = new Result[array.size()];
		for(int i=0;i<array.size();i++) {
			json = array.getJSONObject(i);
			results[i] = new Result(parseConditionValidator(json, referenceResolver), json.getString("alias"));
			setValue(json, results[i], referenceResolver);
		}
		return results;
	}
	// 解析join
	private Join[] parseJoins(JSONArray array, ReferenceParser referenceResolver) {
		if(array == null || array.isEmpty())
			return null;
		Join[] joins = new Join[array.size()];
		JSONObject json;
		for(int i=0;i<array.size();i++) {
			json = array.getJSONObject(i);
			joins[i] = new Join(parseConditionValidator(json, referenceResolver), json.getByteValue("type"), parseTable(json.getJSONObject("table"), referenceResolver), parseConditionGroupWrapper(ConditionType.ON, json, referenceResolver));
		}
		return joins;
	}
	
	// 解析条件组, 包括where, join中的on, having
	protected ConditionGroupWrapper parseConditionGroupWrapper(ConditionType type, JSONObject json, ReferenceParser referenceResolver) {
		ConditionGroup[] array = parseConditionGroups_(json.getJSONArray(type.getJsonKey()), referenceResolver);
		if(array == null)
			return null;
		return new ConditionGroupWrapper(parseConditionValidator(null, referenceResolver), type.getPrefixSql(), array);
	}
	private ConditionGroup[] parseConditionGroups_(JSONArray array, ReferenceParser referenceResolver) {
		if(array == null || array.isEmpty()) 
			return null;
		ConditionGroup[] conditionGroups = new ConditionGroup[array.size()];
		JSONObject json;
		for(int i=0;i<array.size();i++) {
			json = array.getJSONObject(i);
			conditionGroups[i] = new ConditionGroup(parseConditionValidator(json, referenceResolver), parseConditionGroups_(json.getJSONArray("conditionGroups"), referenceResolver), parseConditions(json.getJSONArray("conditions"), referenceResolver), LogicalOP.toValue(json.getByteValue("cgcop")), LogicalOP.toValue(json.getByteValue("op")));
		}
		return conditionGroups;
	}
	// 解析具体的conditions
	private Condition[] parseConditions(JSONArray array, ReferenceParser referenceResolver) {
		if(array == null || array.isEmpty())
			return null;
		Condition[] conditions = new Condition[array.size()];
		JSONObject json;
		for(int i=0;i<array.size();i++) {
			json = array.getJSONObject(i);
			conditions[i] = new Condition(parseConditionValidator(json, referenceResolver), parseValue(json.getJSONObject("left"), referenceResolver), CompareType.toValue(json.getString("cop")), LogicalOP.toValue(json.getByteValue("op")));
			if(conditions[i].unNullCompareOP()) {
				conditions[i].setRights(parseRights(json.getJSONArray("rights"), referenceResolver));
			}
		}
		return conditions;
	}
	// 解析condition中right部分
	private Value[] parseRights(JSONArray array, ReferenceParser referenceResolver) {
		if(array == null || array.isEmpty())
			throw new NullPointerException("条件右边的值不能为空");
		
		Value[] rights = new Value[array.size()];
		for(int i=0;i<array.size();i++) {
			rights[i] = parseValue(array.getJSONObject(i), referenceResolver);
		}
		return rights;
	}

	// 解析group by
	private GroupBy[] parseGroupBys(JSONArray array, ReferenceParser referenceResolver) {
		if(array == null || array.isEmpty())
			return null;
		
		JSONObject json;
		GroupBy[] groupBys = new GroupBy[array.size()];
		for(int i=0;i<array.size();i++) {
			json = array.getJSONObject(i);
			groupBys[i] = new GroupBy(parseConditionValidator(json, referenceResolver));
			setGroupByInfo(groupBys[i], json, referenceResolver);
		}
		return groupBys;
	}
	
	// 解析order by
	private OrderBy[] parseOrderBys(JSONArray array, ReferenceParser referenceResolver) {
		if(array == null || array.isEmpty())
			return null;
		
		JSONObject json;
		OrderBy[] orderBys = new OrderBy[array.size()];
		for(int i=0;i<array.size();i++) {
			json = array.getJSONObject(i);
			orderBys[i] = new OrderBy(parseConditionValidator(json, referenceResolver), json.getByteValue("sort"));
			setGroupByInfo(orderBys[i], json, referenceResolver);
		}
		return orderBys;
	}
	
	// 设置group by的信息
	private void setGroupByInfo(GroupBy groupBy, JSONObject json, ReferenceParser referenceResolver) {
		groupBy.setColumn(json.getString("column"));
		if(groupBy.columnIsEmpty())
			groupBy.setFunction(parseFunction(json.getJSONObject("function"), referenceResolver));
	}
}
