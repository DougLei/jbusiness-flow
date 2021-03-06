package com.douglei.business.flow.parser;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.container.reference.ReferenceContainer;
import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.method.Method;
import com.douglei.business.flow.executer.method.Return;
import com.douglei.business.flow.executer.parameter.DeclaredParameter;
import com.douglei.business.flow.executer.parameter.Scope;
import com.douglei.business.flow.executer.sql.Sql;
import com.douglei.business.flow.parser.action.ActionParsers;
import com.douglei.business.flow.parser.sql.SqlParsers;

/**
 * 针对 commonAction, method, sql的解析器 (这三种配置, 都可被业务流引用)
 * @author DougLei
 */
public class ReferenceParser {
	private ReferenceContainer container;
	private Map<String, CommonActionWrapper> commonActionWrapperMap;
	private Map<String, JSONObject> methodMap;
	private Map<String, JSONObject> sqlMap;
	
	public ReferenceParser(ReferenceContainer container, JSONArray commonActions, JSONArray methods, JSONArray sqls) {
		this.container = container;
		this.commonActionWrapperMap = array2CommonActionWrapperMap("commonAction", commonActions);
		this.methodMap = array2Map("method", methods);
		this.sqlMap = array2Map("sql", sqls);
	}
	
	private Map<String, CommonActionWrapper> array2CommonActionWrapperMap(String key, JSONArray array) {
		if(array==null || array.isEmpty()) {
			return Collections.emptyMap();
		}
		Map<String, CommonActionWrapper> map = new HashMap<String, CommonActionWrapper>();
		JSONObject json;
		for(short i=0;i<array.size();i++) {
			json = array.getJSONObject(i);
			if(map.containsKey(json.getString("name")))
				throw new BusinessFlowParseException("存在相同name("+json.getString("name")+")的" + key);
			map.put(json.getString("name"), new CommonActionWrapper(json.getJSONArray("actions")));
		}
		return map;
	}
	
	private Map<String, JSONObject> array2Map(String key, JSONArray array) {
		if(array==null || array.isEmpty()) {
			return Collections.emptyMap();
		}
		Map<String, JSONObject> map = new HashMap<String, JSONObject>();
		JSONObject json;
		for(short i=0;i<array.size();i++) {
			json = array.getJSONObject(i);
			if(map.containsKey(json.getString("name")))
				throw new BusinessFlowParseException("存在相同name("+json.getString("name")+")的" + key);
			map.put(json.getString("name"), json);
		}
		return map;
	}
	

	// ----------------------------------------------------------------------------
	/**
	 * 解析action
	 * @param actions 为commonAction的name, 或为actions配置数组
	 * @return
	 */
	public Action[] parseAction(Object actions) {
		if(actions instanceof JSONArray) {
			return parseAction_((JSONArray)actions);
		}
		return parseAction_(actions.toString());
	}
	private Action[] parseAction_(JSONArray actionArray) {
		Action[] actions = new Action[actionArray.size()];
		for(short i=0;i<actionArray.size();i++) {
			actions[i] = ActionParsers.parse(actionArray.getJSONObject(i), this);
		}
		return actions;
	}
	private Action[] parseAction_(String name) {
		return commonActionWrapperMap.get(name).parseAction();
	}
	
	// CommonAction的包装类
	private class CommonActionWrapper{
		JSONArray confActions; // 配置的action数组
		Action[] actions; // 解析后的action数组
		CommonActionWrapper(JSONArray confActions) {
			this.confActions = confActions;
		}
		
		Action[] parseAction() {
			if(actions == null) {
				actions = parseAction_(confActions);
			}
			return actions;
		}
	}

	
	// ----------------------------------------------------------------------------
	/**
	 * 解析指定name的sql
	 * @param name
	 * @return
	 */
	public Sql parseSql(String name) {
		Sql sql = container.getSql(name);
		if(sql == null) {
			JSONObject sqlJSON = sqlMap.get(name);
			if(sqlJSON == null) 
				throw new NullPointerException("不存在名为"+name+"的sql");
			
			sql = SqlParsers.parse(name, sqlJSON, this);
			container.putSql(sql);
		}
		return sql;
	}

	
	// ----------------------------------------------------------------------------
	/**
	 * 解析指定name的method
	 * @param name
	 * @return
	 */
	public Method parseMethod(String name) {
		Method method = container.getMethod(name);
		if(method == null) {
			JSONObject methodJSON = methodMap.get(name);
			if(methodJSON == null) 
				throw new NullPointerException("不存在名为"+name+"的方法");
			
			DeclaredParameter[] parameters = ParameterParser.parseDeclaredParameters(methodJSON.getJSONArray("params"), Scope.LOCAL, null);
			Action[] actions = parseAction_(methodJSON.getJSONArray("actions"));
			
			Return return_ = null;
			JSONObject returnJSON = methodJSON.getJSONObject("return");
			if(returnJSON != null) {
				String[] names = null;
				JSONArray array = returnJSON.getJSONArray("names");
				if(array != null && array.size() > 0) {
					names = new String[array.size()];
					array.toArray(names);
				}
				return_ = new Return(returnJSON.getBooleanValue("all"), names);
			}
			
			method = new Method(name, parameters, actions, return_);
			container.putMethod(method);
		}
		return method;
	}
}
