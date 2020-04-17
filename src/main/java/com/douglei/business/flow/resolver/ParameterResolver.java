package com.douglei.business.flow.resolver;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.executer.DataType;
import com.douglei.business.flow.executer.parameter.DeclaredParameter;
import com.douglei.business.flow.executer.parameter.Parameter;
import com.douglei.business.flow.executer.parameter.Scope;

/**
 * 参数解析器
 * @author DougLei
 */
public class ParameterResolver {
	
	/**
	 * 解析定义的参数
	 * @param json
	 * @return
	 */
	public static Parameter parseParameter(JSONObject json) {
		return new Parameter(json.getString("name"), Scope.toValue(json.getByteValue("scope")));
	}
	
	/**
	 * 解析参数
	 * @param array
	 * @return
	 */
	public static Parameter[] parseParameters(JSONArray array) {
		int size = array==null?0:array.size();
		if(size == 0)
			return null;
		
		Parameter[] parameters = new Parameter[size];
		for (byte i=0;i<size;i++) {
			parameters[i] = parseParameter(array.getJSONObject(i));
		}
		return parameters;
	}
	
	
	/**
	 * 解析定义的参数
	 * @param json
	 * @return
	 */
	public static DeclaredParameter parseDeclaredParameters(JSONObject json) {
		return new DeclaredParameter(json.getString("name"),
					Scope.toValue(json.getByteValue("scope")),
					DataType.toValue(json.getString("dataType")),
					json.get("defaultValue"),
					json.getBooleanValue("required"));
	}
	
	/**
	 * 解析定义的参数
	 * @param array
	 * @return
	 */
	public static DeclaredParameter[] parseDeclaredParameters(JSONArray array) {
		int size = array==null?0:array.size();
		if(size == 0)
			return null;
		
		DeclaredParameter[] parameters = new DeclaredParameter[size];
		for (byte i=0;i<size;i++) {
			parameters[i] = parseDeclaredParameters(array.getJSONObject(i));
		}
		return parameters;
	}
}