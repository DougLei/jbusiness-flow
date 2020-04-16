package com.douglei.business.flow.resolver.action.impl.func;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.action.impl.func.method.FuncMethodAction;
import com.douglei.business.flow.executer.action.impl.func.method.Receive;
import com.douglei.business.flow.executer.action.impl.func.method.ReceiveAll;
import com.douglei.business.flow.executer.parameter.Scope;
import com.douglei.business.flow.resolver.ParameterResolver;
import com.douglei.business.flow.resolver.ReferenceResolver;
import com.douglei.business.flow.resolver.action.ActionResolver;

/**
 * 
 * @author DougLei
 */
public class FuncMethodActionResolver extends ActionResolver {

	@Override
	public String getType() {
		return "func_method";
	}
	
	@Override
	public Action parse(JSONObject actionJSON, ReferenceResolver referenceResolver) {
		JSONObject content = actionJSON.getJSONObject("content");
		
		FuncMethodAction action = new FuncMethodAction(
				referenceResolver.parseMethod(content.getString("methodName")),
				ParameterResolver.parse(content.getJSONArray("params")));
		
		byte size;
		JSONObject json;
		JSONArray array = content.getJSONArray("receives");
		if((size = (byte) (array==null?0:array.size())) > 0) {
			Receive[] receives = new Receive[size];
			for(byte i=0;i<size;i++) {
				json = array.getJSONObject(i);
				receives[i] = new Receive(
						json.getString("returnName"),
						json.getString("name"),
						Scope.toValue(json.getByteValue("scope")));
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
			
			action.setReceiveAll(new ReceiveAll(excludeNames, ParameterResolver.parse(json)));
		}
		return action;
	}
}
