package com.douglei.business.flow.resolver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.BFConfiguration;
import com.douglei.business.flow.core.BusinessFlow;
import com.douglei.business.flow.core.event.Event;
import com.douglei.business.flow.core.flow.Flow;
import com.douglei.business.flow.core.param.Parameter;

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
			bf.setInputParameters(inputParamsResolving(json.getJSONArray("params")));
			bf.setStartEvent(buildBFStruct(json.getJSONArray("events"), json.getJSONArray("flows"), new ReferenceResolver(configuration, json.getJSONArray("commonActions"), json.getJSONArray("methods"), json.getJSONArray("sqls"))));
		}
		return bf;
	}
	
	// 解析输入参数
	private List<Parameter> inputParamsResolving(JSONArray params){
		byte size = params==null?0:(byte)params.size();
		if(size == 0) {
			return Collections.emptyList();
		}
		
		List<Parameter> parameters = new ArrayList<Parameter>(size);
		JSONObject param;
		for (byte i=0;i<size;i++) {
			param = params.getJSONObject(i);
			parameters.add(new Parameter(param.getString("name"),
										 param.getString("description"),
										 param.getByteValue("scope"),
										 param.getByteValue("dataType"),
										 param.get("defaultValue"),
										 param.getBoolean("required")));
		}
		return parameters;
	}
	
	// 根据event和flow, 搭建业务流的整体结构
	private Event buildBFStruct(JSONArray events, JSONArray flows, ReferenceResolver referenceResolver) {
		Event startEvent = null;
		
		// 获取起始的事件, 以及所有事件的map集合
		Map<String, Event> eventMap = new HashMap<String, Event>();
		Event event;
		for(short index=0;index<events.size();index++) {
			event = new EventResolver(events.getJSONObject(index)).parse(referenceResolver);
			if(event.isStart()) {
				startEvent = event;
			}
			eventMap.put(event.getName(), event);
		}
		
		// 通过flow, 将event连接起来
		Flow flow;
		for(short index=0;index<flows.size();index++) {
			flow = new FlowResolver(flows.getJSONObject(index)).parse(referenceResolver);
			eventMap.get(flow.getSourceEvent()).linkFlows(flow);// 将sourceEvent和flow关联
			flow.linkNextEvent(eventMap.get(flow.getTargetEvent()));// 将targetEvent和flow关联
		}
		return startEvent;
	}
}
