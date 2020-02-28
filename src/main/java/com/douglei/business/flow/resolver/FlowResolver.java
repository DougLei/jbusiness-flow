package com.douglei.business.flow.resolver;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author DougLei
 */
public class FlowResolver {
	private JSONObject flow;
	private EventResolver targetResolver;
	
	public FlowResolver(JSONObject flow) {
		this.flow = flow;
	}

	public void linkEvents(Map<String, EventResolver> eventResolvers) {
		eventResolvers.get(flow.getString("sourceEvent")).linkFlow(this);
		this.targetResolver = eventResolvers.get(flow.getString("targetEvent"));
	}
}
