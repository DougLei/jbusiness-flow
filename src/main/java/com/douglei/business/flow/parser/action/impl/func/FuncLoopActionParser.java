package com.douglei.business.flow.parser.action.impl.func;

import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.action.impl.func.FuncLoopAction;
import com.douglei.business.flow.executer.parameter.DeclaredParameter;
import com.douglei.business.flow.executer.parameter.Parameter;
import com.douglei.business.flow.executer.parameter.Scope;
import com.douglei.business.flow.parser.ParameterParser;
import com.douglei.business.flow.parser.ReferenceParser;
import com.douglei.business.flow.parser.action.ActionParser;

/**
 * 
 * @author DougLei
 */
public class FuncLoopActionParser extends ActionParser {

	@Override
	public String getType() {
		return "func_loop";
	}

	@Override
	public Action parse(JSONObject actionJSON, ReferenceParser referenceResolver) {
		JSONObject content = actionJSON.getJSONObject("content");
		
		Parameter collection = ParameterParser.parseParameter(content);
		
		JSONObject aliasJSON = content.getJSONObject("alias");
		DeclaredParameter alias = ParameterParser.parseDeclaredParameter(aliasJSON, Scope.LOCAL, null);
		
		Action[] actions = referenceResolver.parseAction(content.get("actions"));
		return new FuncLoopAction(collection, alias, ParameterParser.parseParameter(aliasJSON), actions);
	}
}
