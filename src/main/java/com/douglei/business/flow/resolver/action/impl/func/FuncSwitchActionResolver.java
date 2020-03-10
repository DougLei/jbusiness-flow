package com.douglei.business.flow.resolver.action.impl.func;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.action.impl.func.FuncSwitchAction;
import com.douglei.business.flow.resolver.ConditionResolver;
import com.douglei.business.flow.resolver.ReferenceResolver;
import com.douglei.business.flow.resolver.action.ActionResolver;

/**
 * 
 * @author DougLei
 */
public class FuncSwitchActionResolver extends ActionResolver {

	@Override
	public String getType() {
		return "func_switch";
	}
	
	@Override
	public Action parse(JSONObject actionJSON, ReferenceResolver referenceResolver) {
		JSONArray contents = actionJSON.getJSONArray("content");
		FuncSwitchAction action = new FuncSwitchAction(contents.size());
		JSONObject content;
		for(byte i=0;i<contents.size();i++) {
			content = contents.getJSONObject(i);
			action.add(i, 
					ConditionResolver.parse(content.getJSONArray("conditionGroups"), referenceResolver), 
					referenceResolver.parseAction(content.get("actions")));
		}
		return action;
	}
}
