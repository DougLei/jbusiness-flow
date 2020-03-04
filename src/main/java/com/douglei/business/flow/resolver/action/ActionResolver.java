package com.douglei.business.flow.resolver.action;

import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.core.action.Action;

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
	 * @param action
	 * @return
	 */
	Action parse(JSONObject action);
}
