package com.douglei.business.flow.resolver;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.Constants;

/**
 * 
 * @author DougLei
 */
public class EventResolver {
	private JSONObject event;
	private List<FlowResolver> flowResolvers;
	
	public EventResolver(JSONObject event) {
		this.event = event;
	}

	public boolean isStart() {
		return event.getByteValue("type") == Constants.EVENT_START;
	}
	public String getName() {
		return event.getString("name");
	}
	public void linkFlow(FlowResolver flowResolver) {
		if(flowResolvers == null) {
			flowResolvers = new ArrayList<FlowResolver>(3);
		}
		flowResolvers.add(flowResolver);
	}
}
