package com.douglei.business.flow.resolver.sql;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.executer.Parameter;
import com.douglei.business.flow.executer.sql.Sql;
import com.douglei.business.flow.executer.sql.component.Function;
import com.douglei.business.flow.executer.sql.component.Table;
import com.douglei.business.flow.executer.sql.component.Value;
import com.douglei.business.flow.executer.sql.component.select.GroupAndOrder;
import com.douglei.business.flow.executer.sql.component.select.Result;
import com.douglei.business.flow.executer.sql.component.select.Select;
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
		
		
		// join
		// where
		select.setGroupBys(parseGO(selectJSON.getJSONArray("groupBys")));
		// having
		select.setOrderBys(parseGO(selectJSON.getJSONArray("orderBys")));
		
		
		// TODO 
		
		
		
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
	// join
	// where
	// having
	
	// 解析group by和order by
	private GroupAndOrder[] parseGO(JSONArray array) {
		byte size = array==null?0:(byte)array.size();
		if(size > 0) {
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
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
