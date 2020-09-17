package com.douglei.business.flow.parser.action.impl.func;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.action.impl.func.FuncSwitchAction;
import com.douglei.business.flow.parser.ReferenceParser;
import com.douglei.business.flow.parser.action.ActionParser;
import com.douglei.business.flow.parser.condition.ConditionParser;

/**
 * 
 * @author DougLei
 */
public class FuncSwitchActionParser extends ActionParser {

	@Override
	public String getType() {
		return "func_switch";
	}
	
	@Override
	public Action parse(JSONObject actionJSON, ReferenceParser referenceResolver) {
		JSONArray contents = actionJSON.getJSONArray("content");
		FuncSwitchAction action = new FuncSwitchAction(contents.size());
		JSONObject content;
		for(byte i=0;i<contents.size();i++) {
			content = contents.getJSONObject(i);
			action.add(i, ConditionParser.parse(content.getJSONArray("conditionGroups"), referenceResolver), 
					referenceResolver.parseAction(content.get("actions")));
		}
		return action;
	}
}
