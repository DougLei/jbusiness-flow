package com.douglei.business.flow.resolver;

import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.core.Flow;

/**
 * 
 * @author DougLei
 */
public class FlowResolver {
	private JSONObject flowJson;
	
	public FlowResolver(JSONObject flowJson) {
		this.flowJson = flowJson;
	}
	
	public Flow parse(CommonActionResolver commonActionResolver, MethodResolver methodResolver, SqlResolver sqlResolver) {
		// TODO 解析出flow
		return null;
	}
}
