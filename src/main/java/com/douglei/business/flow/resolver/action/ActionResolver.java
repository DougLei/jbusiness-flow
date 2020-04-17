package com.douglei.business.flow.resolver.action;

import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.executer.DataType;
import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.parameter.ResultParameter;
import com.douglei.business.flow.resolver.ParameterResolver;
import com.douglei.business.flow.resolver.ReferenceResolver;

/**
 * 
 * @author DougLei
 */
public abstract class ActionResolver {
	
	/**
	 * action解析器的类型
	 * @return
	 */
	public abstract String getType();

	/**
	 * 解析
	 * @param actionJSON
	 * @param referenceResolver 
	 * @return
	 */
	public abstract Action parse(JSONObject actionJSON, ReferenceResolver referenceResolver);
	
	/**
	 * 获取配置的result
	 * @param actionJSON
	 * @param assignDataType 指定的数据类型, 如果传入null, 则使用配置的类型
	 * @return
	 */
	protected final ResultParameter getResultParameter(JSONObject actionJSON, DataType assignDataType) {
		JSONObject result = actionJSON.getJSONObject("result");
		return ParameterResolver.parseResultParameter(result, assignDataType);
	}
}
