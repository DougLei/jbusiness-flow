package com.douglei.business.flow.resolver;

import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.executer.Flow;

/**
 * 
 * @author DougLei
 */
public class FlowResolver {
	private JSONObject flowJson;
	
	public FlowResolver(JSONObject flowJson) {
		this.flowJson = flowJson;
	}
	
	public Flow parse(ReferenceResolver referenceResolver) {
		// TODO 解析出flow
		return null;
	}
}
