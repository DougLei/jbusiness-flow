package com.douglei.business.flow.resolver.action;

import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.executer.DataType;
import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.parameter.Parameter;
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
	 * @param defaultDataType 强制指定数据类型, 如果传入null, 则使用配置的类型
	 * @return
	 */
	protected final Parameter getResult(JSONObject actionJSON, DataType forceDataType) {
		JSONObject result = actionJSON.getJSONObject("result");
		if(result == null)
			return null;
		if(forceDataType != null)
			result.put("dataType", forceDataType.name());
		return ParameterResolver.parse(result);
	}
}
