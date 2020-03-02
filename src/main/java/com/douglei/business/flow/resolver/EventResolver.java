package com.douglei.business.flow.resolver;

import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.core.Event;

/**
 * 
 * @author DougLei
 */
public class EventResolver {
	private JSONObject eventJson;
	
	public EventResolver(JSONObject eventJson) {
		this.eventJson = eventJson;
	}

	public Event parse(CommonActionResolver commonActionResolver, MethodResolver methodResolver, SqlResolver sqlResolver) {
		// TODO 解析出event
		return null;
	}
}
