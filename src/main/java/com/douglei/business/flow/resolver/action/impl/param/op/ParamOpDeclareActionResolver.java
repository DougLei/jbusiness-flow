package com.douglei.business.flow.resolver.action.impl.param.op;

import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.executer.Parameter;
import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.action.impl.param.op.ParamOpDeclareAction;
import com.douglei.business.flow.resolver.ParameterResolver;
import com.douglei.business.flow.resolver.ReferenceResolver;
import com.douglei.business.flow.resolver.action.ActionResolver;

/**
 * 
 * @author DougLei
 */
public class ParamOpDeclareActionResolver implements ActionResolver {

	@Override
	public String getType() {
		return "param_op_declare";
	}

	@Override
	public Action parse(JSONObject actionJSON, ReferenceResolver referenceResolver) {
		Parameter parameter = ParameterResolver.parse(actionJSON);
		String paramName = actionJSON.getString("paramName");
		byte paramScope = actionJSON.getByteValue("paramScope");
		return new ParamOpDeclareAction(parameter, paramName, paramScope);
	}
}
