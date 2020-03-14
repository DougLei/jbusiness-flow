package com.douglei.business.flow.resolver;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.executer.DataType;
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
		if(json == null) {
			return null;
		}
		return Parameter.newInstance(json.getString("name"),
					json.getByteValue("scope"),
					DataType.toValue(json.getString("dataType")),
					json.getBoolean("required"),
					json.get("defaultValue"),
					json.getString("description"));
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