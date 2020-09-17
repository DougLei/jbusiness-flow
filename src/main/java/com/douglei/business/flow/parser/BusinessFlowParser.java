package com.douglei.business.flow.parser;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.container.reference.ReferenceContainer;
import com.douglei.business.flow.executer.BusinessFlow;
import com.douglei.business.flow.executer.Event;
import com.douglei.business.flow.executer.Flow;
import com.douglei.business.flow.parser.condition.ConditionParser;
import com.douglei.tools.utils.CollectionUtil;

/**
 * 业务流解析器
 * @author DougLei
 */
public class BusinessFlowParser {
	private ReferenceContainer referenceContainer;
	
	public BusinessFlowParser(ReferenceContainer referenceContainer) {
		this.referenceContainer = referenceContainer;
	}

	public BusinessFlow parse(String bfjson) {
		JSONObject json = JSONObject.parseObject(bfjson);
		if(!json.getBooleanValue("enabled"))
			throw new BusinessFlowParseException(json.getString("name") + "("+json.getString("description")+")流程未被激活, 无法解析");
		BusinessFlow bf = new BusinessFlow(json.getString("name"), json.getString("version"));
		bf.setInputParameters(ParameterParser.parseDeclaredParameters(json.getJSONArray("params"), null, null));
		bf.setStartEvent(buildBFStruct(json.getJSONArray("events"), json.getJSONArray("flows"), new ReferenceParser(referenceContainer, json.getJSONArray("commonActions"), json.getJSONArray("methods"), json.getJSONArray("sqls"))));
		return bf;
	}
	
	// 根据event和flow, 搭建业务流的整体结构
	private Event buildBFStruct(JSONArray events, JSONArray flows, ReferenceParser referenceResolver) {
		Event startEvent = null;
		JSONObject json;
		
		// 获取起始的事件, 以及所有事件的map集合
		Map<String, Event> eventMap = new HashMap<String, Event>();
		Event event;
		for(int index=0;index<events.size();index++) {
			json = events.getJSONObject(index);
			event = new Event(json.getByteValue("type"), 
							  json.getString("name"), 
							  referenceResolver.parseAction(json.get("actions")));
			if(event.isStart()) {
				if(startEvent != null)
					throw new BusinessFlowParseException("业务流中只能配置一个起始事件"); 
				startEvent = event;
			}
			eventMap.put(event.getName(), event);
		}
		
		// 通过flow, 将event连接起来, 如果只有一个节点, 可以没有连线
		if(CollectionUtil.unEmpty(flows)) {
			Flow flow;
			for(int index=0;index<flows.size();index++) {
				json = flows.getJSONObject(index);
				flow = new Flow(json.getByteValue("type"), 
								json.getByteValue("order"), 
								json.getString("sourceEvent"), 
								json.getString("targetEvent"),
								ConditionParser.parse(json.getJSONArray("conditionGroups"), referenceResolver));
				eventMap.get(flow.getSourceEvent()).linkFlows(flow);// 将sourceEvent和flow关联
				flow.linkEvent(eventMap.get(flow.getTargetEvent()));// 将targetEvent和flow关联
			}
		}
		return startEvent;
	}
}
