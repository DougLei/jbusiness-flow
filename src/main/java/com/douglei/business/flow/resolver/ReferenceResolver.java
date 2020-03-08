package com.douglei.business.flow.resolver;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.BFConfiguration;
import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.method.Method;
import com.douglei.business.flow.resolver.action.ActionResolvers;
import com.douglei.tools.utils.CollectionUtil;

/**
 * 针对 commonAction, method, sql的解析器 (这三种配置, 都可被业务流引用)
 * @author DougLei
 */
public class ReferenceResolver {
	private BFConfiguration configuration;
	
	private Map<String, JSONObject> commonActionMap;
	private Map<String, JSONObject> methodMap;
	private Map<String, JSONObject> sqlMap;
	
	public ReferenceResolver(BFConfiguration configuration, JSONArray commonActions, JSONArray methods, JSONArray sqls) {
		this.configuration = configuration;
		this.commonActionMap = array2Map(commonActions);
		this.methodMap = array2Map(methods);
		this.sqlMap = array2Map(sqls);
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
		// TODO Auto-generated method stub
		return null;
	}

	
	// ----------------------------------------------------------------------------
	/**
	 * 解析指定name的sql
	 * @param name
	 * @return
	 */
	public String parseSql(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	
	// ----------------------------------------------------------------------------
	/**
	 * 解析指定name的method
	 * @param name
	 * @return
	 */
	public Method parseMethod(String name) {
		// TODO Auto-generated method stub
		return null;
	}
}
