package com.douglei.business.flow.resolver.action;

import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.executer.Parameter;
import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.resolver.ParameterResolver;
import com.douglei.business.flow.resolver.ReferenceResolver;

/**
 * 
 * @author DougLei
 */
public interface ActionResolver {
	
	/**
	 * action解析器的类型
	 * @return
	 */
	String getType();

	/**
	 * 解析
	 * @param actionJSON
	 * @param referenceResolver 
	 * @return
	 */
	Action parse(JSONObject actionJSON, ReferenceResolver referenceResolver);
	
	/**
	 * 获取配置的result
	 * @param actionJSON
	 * @return
	 */
	default Parameter getResult(JSONObject actionJSON) {
		return ParameterResolver.parse(actionJSON.getJSONObject("result"));
	}
}
