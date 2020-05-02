package com.douglei.business.flow.resolver.action.impl.func;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.action.impl.func.method.FuncMethodAction;
import com.douglei.business.flow.executer.action.impl.func.method.Receive;
import com.douglei.business.flow.executer.action.impl.func.method.ReceiveAll;
import com.douglei.business.flow.executer.parameter.InvokerParameter;
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
				parseInvokerParameters(content.getJSONArray("params")));
		
		byte size;
		JSONObject json;
		JSONArray array = content.getJSONArray("receives");
		if((size = (byte) (array==null?0:array.size())) > 0) {
			Receive[] receives = new Receive[size];
			for(byte i=0;i<size;i++) {
				json = array.getJSONObject(i);
				receives[i] = new Receive(
						json.getString("returnName"),
						ParameterResolver.parseResultParameter(json, null));
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
			
			action.setReceiveAll(new ReceiveAll(excludeNames, ParameterResolver.parseResultParameter(json, null)));
		}
		return action;
	}
	
	/**
	 * 解析调用者参数
	 * @param array
	 * @return
	 */
	protected InvokerParameter[] parseInvokerParameters(JSONArray array) {
		int size = array==null?0:array.size();
		if(size == 0)
			return null;
		
		JSONObject json;
		InvokerParameter[] parameters = new InvokerParameter[size];
		for (byte i=0;i<size;i++) {
			json = array.getJSONObject(i);
			parameters[i] = new InvokerParameter(json.getString("name"), Scope.toValue(json.getByteValue("scope")), json.get("defaultValue"), json.getString("targetName"));
		}
		return parameters;
	}
}
