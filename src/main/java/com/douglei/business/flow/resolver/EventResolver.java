package com.douglei.business.flow.resolver;

import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.core.event.Event;

/**
 * 
 * @author DougLei
 */
public class EventResolver {
	private JSONObject eventJson;
	
	public EventResolver(JSONObject eventJson) {
		this.eventJson = eventJson;
	}

	public Event parse(ReferenceResolver referenceResolver) {
		Event event = new Event(eventJson.getByteValue("type"), eventJson.getString("name"), eventJson.getString("description"));
		
		// TODO 解析出event
		return null;
	}
}
