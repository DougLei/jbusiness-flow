package com.douglei.business.flow.parser.action.impl.func;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.action.impl.func.method.FuncMethodAction;
import com.douglei.business.flow.executer.action.impl.func.method.Receive;
import com.douglei.business.flow.executer.action.impl.func.method.ReceiveAll;
import com.douglei.business.flow.parser.ParameterParser;
import com.douglei.business.flow.parser.ReferenceParser;
import com.douglei.business.flow.parser.action.ActionParser;

/**
 * 
 * @author DougLei
 */
public class FuncMethodActionParser extends ActionParser {

	@Override
	public String getType() {
		return "func_method";
	}
	
	@Override
	public Action parse(JSONObject actionJSON, ReferenceParser referenceResolver) {
		JSONObject content = actionJSON.getJSONObject("content");
		
		FuncMethodAction action = new FuncMethodAction(
				referenceResolver.parseMethod(content.getString("methodName")),
				ParameterParser.parseInvokerParameters(content.getJSONArray("params")));
		
		byte size;
		JSONObject json;
		JSONArray array = content.getJSONArray("receives");
		if((size = (byte) (array==null?0:array.size())) > 0) {
			Receive[] receives = new Receive[size];
			for(byte i=0;i<size;i++) {
				json = array.getJSONObject(i);
				receives[i] = new Receive(
						json.getString("returnName"),
						ParameterParser.parseResultParameter(json, null));
			}
			action.setReceives(receives);
		}else if((json = content.getJSONObject("receiveAll")) != null) {
			String[] excludeNames = null; 
			array = json.getJSONArray("excludeNames");
			size = (byte) (array==null?0:array.size());
			if(size > 0) {
				excludeNames = new String[size];
				array.toArray(excludeNames);
			}
			
			action.setReceiveAll(new ReceiveAll(excludeNames, ParameterParser.parseResultParameter(json, null)));
		}
		return action;
	}
}
