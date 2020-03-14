package com.douglei.business.flow.resolver.action.impl.param.op;

import com.alibaba.fastjson.JSONArray;
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
public class ParamOpDeclareActionResolver extends ActionResolver {

	@Override
	public String getType() {
		return "param_op_declare";
	}

	@Override
	public Action parse(JSONObject actionJSON, ReferenceResolver referenceResolver) {
		JSONArray contents = actionJSON.getJSONArray("content");
		
		byte size = (byte) contents.size();
		ParamOpDeclareAction action = new ParamOpDeclareAction(size);

		JSONObject content;
		Parameter parameter;
		for(byte i=0;i<size;i++) {
			content = contents.getJSONObject(i);
			parameter = ParameterResolver.parse(content);
			action.addParam(i, parameter);
			
			if(parameter.getDefaultValue() == null) {
				action.addRefParam(i, Parameter.newInstance(content.getString("refParamName"), content.getByteValue("refParamScope")));
			}
		}
		return action;
	}
}
