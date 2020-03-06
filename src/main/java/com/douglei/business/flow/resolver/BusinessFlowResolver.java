package com.douglei.business.flow.resolver;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.BFConfiguration;
import com.douglei.business.flow.executer.BusinessFlow;
import com.douglei.business.flow.executer.Event;
import com.douglei.business.flow.executer.Flow;

/**
 * 业务流解析器
 * @author DougLei
 */
public class BusinessFlowResolver {
	private BFConfiguration configuration;
	
	public BusinessFlowResolver(BFConfiguration configuration) {
		this.configuration = configuration;
	}

	public BusinessFlow parse(String bfjson) {
		JSONObject json = JSONObject.parseObject(bfjson);
		BusinessFlow bf = new BusinessFlow(json.getString("name"), json.getString("description"), json.getString("version"), json.getBooleanValue("state"));
		if(bf.isEnabled()) {
			bf.setInputParameters(ParameterResolver.parse(json.getJSONArray("params")));
			bf.setStartEvent(buildBFStruct(json.getJSONArray("events"), json.getJSONArray("flows"), new ReferenceResolver(configuration, json.getJSONArray("commonActions"), json.getJSONArray("methods"), json.getJSONArray("sqls"))));
		}
		return bf;
	}
	
	// 根据event和flow, 搭建业务流的整体结构
	private Event buildBFStruct(JSONArray events, JSONArray flows, ReferenceResolver referenceResolver) {
		Event startEvent = null;
		
		// 获取起始的事件, 以及所有事件的map集合
		Map<String, Event> eventMap = new HashMap<String, Event>();
		Event event;
		for(short index=0;index<events.size();index++) {
			event = eventParse(events.getJSONObject(index), referenceResolver);
			if(event.isStart()) {
				startEvent = event;
			}
			eventMap.put(event.getName(), event);
		}
		
		// 通过flow, 将event连接起来
		Flow flow;
		for(short index=0;index<flows.size();index++) {
			flow = flowParse(flows.getJSONObject(index), referenceResolver);
			eventMap.get(flow.getSourceEvent()).linkFlows(flow);// 将sourceEvent和flow关联
			flow.linkNextEvent(eventMap.get(flow.getTargetEvent()));// 将targetEvent和flow关联
		}
		return startEvent;
	}

	// 解析event
	private Event eventParse(JSONObject eventJson, ReferenceResolver referenceResolver) {
		Event event = new Event(eventJson.getByteValue("type"), eventJson.getString("name"), eventJson.getString("description"));
		event.setActions(referenceResolver.parseAction(eventJson.get("actions")));
		return event;
	}
	
	// 解析flow
	private Flow flowParse(JSONObject jsonObject, ReferenceResolver referenceResolver) {
		// TODO Auto-generated method stub
		return null;
	}
}
