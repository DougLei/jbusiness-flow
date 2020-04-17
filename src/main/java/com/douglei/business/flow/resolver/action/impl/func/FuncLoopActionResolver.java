package com.douglei.business.flow.resolver.action.impl.func;

import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.action.impl.func.FuncLoopAction;
import com.douglei.business.flow.executer.parameter.DeclaredParameter;
import com.douglei.business.flow.resolver.ParameterResolver;
import com.douglei.business.flow.resolver.ReferenceResolver;
import com.douglei.business.flow.resolver.action.ActionResolver;

/**
 * 
 * @author DougLei
 */
public class FuncLoopActionResolver extends ActionResolver {

	@Override
	public String getType() {
		return "func_loop";
	}

	@Override
	public Action parse(JSONObject actionJSON, ReferenceResolver referenceResolver) {
		JSONObject content = actionJSON.getJSONObject("content");
		DeclaredParameter collection = ParameterResolver.parse(content);
		DeclaredParameter alias = ParameterResolver.parse(content.getJSONObject("alias"));
		Action[] actions = referenceResolver.parseAction(content.get("actions"));
		return new FuncLoopAction(collection, alias, actions);
	}
}
