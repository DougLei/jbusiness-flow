package com.douglei.business.flow.resolver.action.impl.func;

import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.resolver.ReferenceResolver;
import com.douglei.business.flow.resolver.action.ActionResolver;

/**
 * 
 * @author DougLei
 */
public class FuncMethodActionResolver implements ActionResolver {

	@Override
	public String getType() {
		return "func_method";
	}
	
	@Override
	public Action parse(JSONObject actionJSON, ReferenceResolver referenceResolver) {
		JSONObject content = actionJSON.getJSONObject("content");
		// TODO Auto-generated method stub
		return null;
	}
}
