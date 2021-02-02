package com.douglei.business.flow.parser.action.impl.param.op;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.action.impl.param.op.ParamOpDeclareAction;
import com.douglei.business.flow.executer.parameter.DeclaredParameter;
import com.douglei.business.flow.executer.parameter.Parameter;
import com.douglei.business.flow.executer.parameter.Scope;
import com.douglei.business.flow.parser.ParameterParser;
import com.douglei.business.flow.parser.ReferenceParser;
import com.douglei.business.flow.parser.action.ActionParser;
import com.douglei.tools.StringUtil;

/**
 * 
 * @author DougLei
 */
public class ParamOpDeclareActionParser extends ActionParser {

	@Override
	public String getType() {
		return "param_op_declare";
	}

	@Override
	public Action parse(JSONObject actionJSON, ReferenceParser referenceResolver) {
		JSONArray contents = actionJSON.getJSONArray("content");
		
		byte size = (byte) contents.size();
		ParamOpDeclareAction action = new ParamOpDeclareAction(size);

		JSONObject content;
		DeclaredParameter declaredParameter;
		for(byte i=0;i<size;i++) {
			content = contents.getJSONObject(i);
			declaredParameter = ParameterParser.parseDeclaredParameter(content, null, null);
			action.addDeclareParameter(i, declaredParameter);
			
			if(declaredParameter.getDefaultValue() == null) {
				String refParamName = content.getString("refParamName");
				if(StringUtil.unEmpty(refParamName)) {
					action.addRefParameter(i, new Parameter(refParamName, Scope.toValue(content.getByteValue("refParamScope")), null));
				}
			}
		}
		return action;
	}
}
