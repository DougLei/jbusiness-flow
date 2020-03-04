package com.douglei.business.flow.resolver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.BFConfiguration;
import com.douglei.business.flow.core.action.Action;
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

	/**
	 * 解析action
	 * @param actions 为commonAction的name, 或为actions配置数组
	 * @return
	 */
	public List<Action> parseAction(Object actions) {
		if(actions instanceof JSONArray) {
			return parseAction_((JSONArray)actions);
		}
		return parseAction_(actions.toString());
	}
	private List<Action> parseAction_(JSONArray actionArray) {
		List<Action> actions = new ArrayList<Action>(actionArray.size());
		for(short i=0;i<actionArray.size();i++) {
			actions.add(ActionResolvers.parse(actionArray.getJSONObject(i)));
		}
		return actions;
	}
	private List<Action> parseAction_(String name) {
		// TODO Auto-generated method stub
		return null;
	}
}
