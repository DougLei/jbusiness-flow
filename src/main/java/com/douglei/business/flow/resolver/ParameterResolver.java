package com.douglei.business.flow.resolver;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.executer.Parameter;

/**
 * 
 * @author DougLei
 */
public class ParameterResolver {
	private static final Parameter[] EMPTY_PARAMETERS = new Parameter[0]; // 空的参数数组
	
	/**
	 * 解析获取单个参数
	 * @param json
	 * @return
	 */
	public static Parameter parse(JSONObject json) {
		return new Parameter(json.getString("name"),
				json.getByteValue("scope"),
				json.getString("description"),
				json.getString("dataType"),
				json.get("value"),
				json.getBoolean("required"),
				json.getString("refParamName"),
				json.getByteValue("refParamScope"));
	}
	
	/**
	 * 解析获取参数数组
	 * @param array
	 * @return
	 */
	public static Parameter[] parse(JSONArray array) {
		int size = array==null?0:array.size();
		if(size == 0) {
			return EMPTY_PARAMETERS;
		}
		
		Parameter[] parameters = new Parameter[size];
		for (byte i=0;i<size;i++) {
			parameters[i] = parse(array.getJSONObject(i));
		}
		return parameters;
	}
}