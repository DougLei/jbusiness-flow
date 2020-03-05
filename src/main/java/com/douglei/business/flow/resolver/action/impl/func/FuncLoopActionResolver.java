package com.douglei.business.flow.resolver.action.impl.func;

import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.core.Parameter;
import com.douglei.business.flow.core.action.Action;
import com.douglei.business.flow.core.action.impl.func.FuncLoopAction;
import com.douglei.business.flow.resolver.ParameterResolver;
import com.douglei.business.flow.resolver.ReferenceResolver;
import com.douglei.business.flow.resolver.action.ActionResolver;

/**
 * 
 * @author DougLei
 */
public class FuncLoopActionResolver implements ActionResolver {

	@Override
	public String getType() {
		return "func_loop";
	}

	@Override
	public Action parse(JSONObject actionJSON, ReferenceResolver referenceResolver) {
		Parameter collection = ParameterResolver.parse(actionJSON);
		String alias = actionJSON.getString("alias");
		Action[] actions = referenceResolver.parseAction(actionJSON.get("actions"));
		return new FuncLoopAction(collection, alias, actions);
	}
}
