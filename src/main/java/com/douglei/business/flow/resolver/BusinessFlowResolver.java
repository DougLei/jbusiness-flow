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
import com.douglei.business.flow.core.Event;
import com.douglei.business.flow.core.Flow;
import com.douglei.business.flow.core.Parameter;

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
			CommonActionResolver commonActionResolver = CommonActionResolver.instance(json.getJSONArray("commonActions"));
			MethodResolver methodResolver = MethodResolver.instance(json.getJSONArray("methods"));
			SqlResolver sqlResolver = SqlResolver.instance(configuration, json.getJSONArray("sqls"));
			
			bf.setInputParameters(inputParamsResolving(json.getJSONArray("params")));
			bf.setStartEvent(buildBFStruct(json.getJSONArray("events"), json.getJSONArray("flows"), commonActionResolver, methodResolver, sqlResolver));
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
	private Event buildBFStruct(JSONArray events, JSONArray flows, CommonActionResolver commonActionResolver, MethodResolver methodResolver, SqlResolver sqlResolver) {
		Event startEvent = null;
		
		// 获取起始的事件, 以及所有事件的map集合
		short size = (short) events.size();
		Map<String, Event> eventMap = new HashMap<String, Event>(size>8?16:8);
		Event event;
		for(byte i=0;i<size;i++) {
			event = new EventResolver(events.getJSONObject(i)).parse(commonActionResolver, methodResolver, sqlResolver);
			if(event.isStart()) {
				startEvent = event;
			}
			eventMap.put(event.getName(), event);
		}
		
		// 通过flow, 将event连接起来
		size = (short) flows.size();
		Flow flow;
		for(byte i=0;i<size;i++) {
			flow = new FlowResolver(flows.getJSONObject(i)).parse(commonActionResolver, methodResolver, sqlResolver);
			eventMap.get(flow.getSourceEvent()).linkFlows(flow);// 将sourceEvent和flow关联
			flow.linkNextEvent(eventMap.get(flow.getTargetEvent()));// 将targetEvent和flow关联
		}
		return startEvent;
	}
}
