package com.douglei.business.flow.resolver;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.executer.DataType;
import com.douglei.business.flow.executer.parameter.Parameter;
import com.douglei.business.flow.executer.parameter.Scope;

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
		return parse(json, null);
	}
	
	/**
	 * 解析获取单个参数
	 * @param json
	 * @param forceScope 强制指定的范围, 会覆盖json中配置的范围
	 * @return
	 */
	public static Parameter parse(JSONObject json, Scope forceScope) {
		if(json == null)
			return null;
		return Parameter.newInstance(json.getString("name"),
					forceScope==null?Scope.toValue(json.getByteValue("scope")):forceScope,
					DataType.toValue(json.getString("dataType")),
					json.get("defaultValue"),
					json.getBooleanValue("required"));
	}
	
	/**
	 * 解析获取参数数组
	 * @param array
	 * @return
	 */
	public static Parameter[] parse(JSONArray array) {
		return parse(array, null);
	}
	
	/**
	 * 解析获取参数数组
	 * @param array
	 * @param forceScope 强制指定的范围, 会覆盖json中配置的范围
	 * @return
	 */
	public static Parameter[] parse(JSONArray array, Scope forceScope) {
		int size = array==null?0:array.size();
		if(size == 0) {
			return EMPTY_PARAMETERS;
		}
		
		Parameter[] parameters = new Parameter[size];
		for (byte i=0;i<size;i++) {
			parameters[i] = parse(array.getJSONObject(i), forceScope);
		}
		return parameters;
	}
}