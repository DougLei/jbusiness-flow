package com.douglei.business.flow.resolver.action.impl.data;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.action.impl.data.op.Data;
import com.douglei.business.flow.executer.parameter.Parameter;
import com.douglei.business.flow.resolver.ParameterResolver;
import com.douglei.business.flow.resolver.ReferenceResolver;
import com.douglei.business.flow.resolver.action.ActionResolver;
import com.douglei.business.flow.resolver.action.ActionResolvers;

/**
 * 
 * @author DougLei
 */
public abstract class DataOpResolver extends ActionResolver {
	private static final String ACTION_TYPE = "func_method";
	
	protected Data parseData(JSONObject json, ReferenceResolver referenceResolver) {
		Data data = new Data(json.getString("format"));
		
		Object obj = null;
		if((obj = json.get("value")) != null) {
			data.setValue(obj);
		}else if((obj = ParameterResolver.parse(json)) != null) {
			data.setParameter((Parameter)obj);
		}else if((obj = json.getJSONObject("action")) != null) {
			JSONObject action = (JSONObject) obj;
			Action[] actions = referenceResolver.parseAction(action.get("actions"));
			
			JSONObject resultPick = action.getJSONObject("resultPick");
			String[] names = null;
			JSONArray array = resultPick.getJSONArray("names");
			if(array != null && array.size() >0) {
				names = new String[array.size()];
				array.toArray(names);
			}
			
			data.setAction(actions, resultPick.getBooleanValue("all"), names);
		}else if((obj = json.getJSONObject("method")) != null) {
			JSONObject actionJSON = new JSONObject(4);
			actionJSON.put("content", obj);
			data.setMethod(ActionResolvers.getActionResolver(ACTION_TYPE).parse(actionJSON, referenceResolver));
		}else {
			throw new NullPointerException("data数据配置不能为空, value/parameter/action/method必须配置一个");
		}
		return data;
	}
}
