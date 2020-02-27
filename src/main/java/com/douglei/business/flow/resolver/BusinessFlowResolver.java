package com.douglei.business.flow.resolver;

import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.BFConfiguration;
import com.douglei.business.flow.core.BusinessFlow;

/**
 * 业务流解析器
 * @author DougLei
 */
public class BusinessFlowResolver {
	private ParamResolver paramResolver;
	private EventResolver eventResolver;
	private FlowResolver flowResolver;
	private CommonActionsResolver commonActionsResolver;
	private MethodResolver methodResolver;
	private SqlResolver sqlResolver;
	
	public BusinessFlowResolver(BFConfiguration configuration) {
		paramResolver = new ParamResolver();
		eventResolver = new EventResolver();
		flowResolver = new FlowResolver();
		commonActionsResolver = new CommonActionsResolver();
		methodResolver = new MethodResolver();
		sqlResolver = new SqlResolver(configuration);
	}

	public BusinessFlow parse(String bfjson) {
		JSONObject json = JSONObject.parseObject(bfjson);
		BusinessFlow bf = new BusinessFlow(json.getString("name"), json.getString("description"), json.getString("version"), json.getByteValue("state"));
		
//		TODO bf.setEvent(event);
		
		
		return bf;
	}
}
