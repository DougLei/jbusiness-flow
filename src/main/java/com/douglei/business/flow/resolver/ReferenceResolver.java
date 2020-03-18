package com.douglei.business.flow.resolver;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.container.reference.ReferenceContainer;
import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.method.Method;
import com.douglei.business.flow.executer.method.Return;
import com.douglei.business.flow.executer.parameter.Parameter;
import com.douglei.business.flow.executer.sql.Sql;
import com.douglei.business.flow.resolver.action.ActionResolvers;
import com.douglei.business.flow.resolver.sql.SqlResolvers;
import com.douglei.tools.utils.CollectionUtil;

/**
 * 针对 commonAction, method, sql的解析器 (这三种配置, 都可被业务流引用)
 * @author DougLei
 */
public class ReferenceResolver {
	private ReferenceContainer container;
	private Map<String, CommonActionWrapper> commonActionWrapperMap;
	private Map<String, JSONObject> methodMap;
	private Map<String, JSONObject> sqlMap;
	
	public ReferenceResolver(ReferenceContainer container, JSONArray commonActions, JSONArray methods, JSONArray sqls) {
		this.container = container;
		this.commonActionWrapperMap = array2CommonActionWrapperMap(commonActions);
		this.methodMap = array2Map(methods);
		this.sqlMap = array2Map(sqls);
	}
	
	private Map<String, CommonActionWrapper> array2CommonActionWrapperMap(JSONArray array) {
		if(CollectionUtil.isEmpty(array)) {
			return Collections.emptyMap();
		}
		Map<String, CommonActionWrapper> map = new HashMap<String, CommonActionWrapper>();
		JSONObject json;
		for(short i=0;i<array.size();i++) {
			json = array.getJSONObject(i);
			map.put(json.getString("name"), new CommonActionWrapper(json.getString("description"), json.getJSONArray("actions")));
		}
		return map;
	}
	
	private Map<String, JSONObject> array2Map(JSONArray array) {
		if(CollectionUtil.isEmpty(array)) {
			return Collections.emptyMap();
		}
		Map<String, JSONObject> map = new HashMap<String, JSONObject>();
		JSONObject json;
		for(short i=0;i<array.size();i++) {
			json = array.getJSONObject(i);
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
			actions[i] = ActionResolvers.parse(actionArray.getJSONObject(i), this);
		}
		return actions;
	}
	private Action[] parseAction_(String name) {
		return commonActionWrapperMap.get(name).parseAction();
	}
	
	// CommonAction的包装类
	private class CommonActionWrapper{
		String description;
		JSONArray confActions; // 配置的action数组
		Action[] actions; // 解析后的action数组
		CommonActionWrapper(String description, JSONArray confActions) {
			this.description = description;
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
			sql = SqlResolvers.parse(name, sqlMap.get(name));
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
			
			Parameter[] parameters = ParameterResolver.parse(methodJSON.getJSONArray("params"));
			Action[] actions = parseAction_(methodJSON.getJSONArray("actions"));
			
			JSONObject returnJSON = methodJSON.getJSONObject("return");
			String[] names = null;
			JSONArray array = returnJSON.getJSONArray("names");
			if(array != null && array.size() > 0) {
				names = new String[array.size()];
				array.toArray(names);
			}
			Return return_ = new Return(returnJSON.getBooleanValue("all"), names);
			
			method = new Method(name, methodJSON.getString("description"), parameters, actions, return_);
			container.putMethod(method);
		}
		return method;
	}
}
