package com.douglei.business.flow.resolver.action.impl.param.op;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.action.impl.param.op.ParamOpDeclareAction;
import com.douglei.business.flow.executer.parameter.DeclaredParameter;
import com.douglei.business.flow.executer.parameter.Parameter;
import com.douglei.business.flow.executer.parameter.Scope;
import com.douglei.business.flow.resolver.ParameterResolver;
import com.douglei.business.flow.resolver.ReferenceResolver;
import com.douglei.business.flow.resolver.action.ActionResolver;
import com.douglei.tools.utils.StringUtil;

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
		DeclaredParameter declaredParameter;
		for(byte i=0;i<size;i++) {
			content = contents.getJSONObject(i);
			declaredParameter = ParameterResolver.parseDeclaredParameter(content, null);
			action.addDeclareParameter(i, declaredParameter);
			
			if(declaredParameter.getDefaultValue() == null) {
				String refParamName = content.getString("refParamName");
				if(StringUtil.notEmpty(refParamName)) {
					action.addRefParameter(i, new Parameter(refParamName, Scope.toValue(content.getByteValue("refParamScope")), null));
				}
			}
		}
		return action;
	}
}
