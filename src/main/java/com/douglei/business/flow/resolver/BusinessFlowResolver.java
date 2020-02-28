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
import com.douglei.business.flow.core.param.Parameter;

/**
 * 业务流解析器
 * @author DougLei
 */
public class BusinessFlowResolver {
	
	public BusinessFlowResolver(BFConfiguration configuration) {
	}

	public BusinessFlow parse(String bfjson) {
		JSONObject json = JSONObject.parseObject(bfjson);
		BusinessFlow bf = new BusinessFlow(json.getString("name"), json.getString("description"), json.getString("version"), json.getBooleanValue("state"));
		if(bf.isEnabled()) {
			bf.setInputParameters(inputParamsResolving(json.getJSONArray("params")));
			
			EventResolver startEventResolver = buildBFStruct(json.getJSONArray("events"), json.getJSONArray("flows"));
			
//			eventResolver.parse(json.getJSONArray("events"));
//			flowResolver.parse(json.getJSONArray("flows"));
			
//			commonActionsResolver.parse(json.getJSONArray("commonActions"));
//			methodResolver.parse(json.getJSONArray("methods"));
//			sqlResolver.parse(json.getJSONArray("sqls"));
			
			
//			TODO bf.setEvent(event);
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
	private EventResolver buildBFStruct(JSONArray events, JSONArray flows) {
		// 获取起始的事件, 以及所有事件的map集合
		short size = (short) events.size();
		EventResolver startEventResolver = null;
		Map<String, EventResolver> eventResolvers = new HashMap<String, EventResolver>(size>8?16:8);
		EventResolver er;
		for(byte i=0;i<size;i++) {
			er = new EventResolver(events.getJSONObject(i));
			if(er.isStart()) {
				startEventResolver = er;
			}
			eventResolvers.put(er.getName(), er);
		}
		
		// 通过flow, 将event连接起来
		size = (short) flows.size();
		for(byte i=0;i<size;i++) {
			new FlowResolver(flows.getJSONObject(i)).linkEvents(eventResolvers);
		}
		return startEventResolver;
	}
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		JSONObject json = JSONObject.parseObject("{'name':'金石磊'}");
		System.out.println(json.getBoolean("xx"));
		System.out.println(json.getJSONArray("xx"));
		System.out.println(json.getByteValue("xx"));
	}
}
