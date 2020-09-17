package com.douglei.business.flow.parser;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.executer.DataType;
import com.douglei.business.flow.executer.parameter.DeclaredParameter;
import com.douglei.business.flow.executer.parameter.InvokerParameter;
import com.douglei.business.flow.executer.parameter.Parameter;
import com.douglei.business.flow.executer.parameter.ResultParameter;
import com.douglei.business.flow.executer.parameter.Scope;
import com.douglei.tools.utils.StringUtil;

/**
 * 参数解析器
 * @author DougLei
 */
public class ParameterParser {
	
	// -----------------------------------------------------------------------------------------------------------
	/**
	 * 解析参数
	 * @param json
	 * @return
	 */
	public static Parameter parseParameter(JSONObject json) {
		Boolean nameRequired = json.getBoolean("nameRequired");
		return new Parameter(json.getString("name"), (nameRequired==null?true:nameRequired), Scope.toValue(json.getByteValue("scope")), json.get("defaultValue"));
	}
	
	// -----------------------------------------------------------------------------------------------------------
	/**
	 * 解析结果参数
	 * @param json
	 * @param assignDataType 指定的数据类型, 如果传入null, 则使用配置的类型
	 * @return
	 */
	public static ResultParameter parseResultParameter(JSONObject json, DataType assignDataType) {
		if(json == null)
			return null;
		
		DataType dataType = assignDataType;
		if(dataType == null) {
			String dt = json.getString("dataType");
			dataType = StringUtil.isEmpty(dt)?null:DataType.toValue(dt);
		}
		return new ResultParameter(json.getString("name"), Scope.toValue(json.getByteValue("scope")), dataType);
	}
	
	// -----------------------------------------------------------------------------------------------------------
	/**
	 * 解析调用者参数
	 * @param array
	 * @return
	 */
	public static  InvokerParameter[] parseInvokerParameters(JSONArray array) {
		int size = array==null?0:array.size();
		if(size == 0)
			return null;
		
		JSONObject json;
		InvokerParameter[] parameters = new InvokerParameter[size];
		for (byte i=0;i<size;i++) {
			json = array.getJSONObject(i);
			parameters[i] = new InvokerParameter(json.getString("name"), Scope.toValue(json.getByteValue("scope")), json.get("defaultValue"), json.getString("targetName"));
		}
		return parameters;
	}
	
	// -----------------------------------------------------------------------------------------------------------
	/**
	 * 解析定义参数
	 * @param json
	 * @param assignScope 指定的范围, 如果传入null, 则使用配置的范围
	 * @param assignDataType 指定的数据类型, 如果传入null, 则使用配置的类型
	 * @return
	 */
	public static DeclaredParameter parseDeclaredParameter(JSONObject json, Scope assignScope, DataType assignDataType) {
		return new DeclaredParameter(json.getString("name"),
					(assignScope==null?Scope.toValue(json.getByteValue("scope")):assignScope),
					(assignDataType==null?DataType.toValue(json.getString("dataType")):assignDataType),
					json.get("defaultValue"),
					json.getBooleanValue("required"));
	}
	
	/**
	 * 解析定义参数
	 * @param array
	 * @param assignScope 指定的范围, 如果传入null, 则使用配置的范围
	 * @param assignDataType 指定的数据类型, 如果传入null, 则使用配置的类型
	 * @return
	 */
	public static DeclaredParameter[] parseDeclaredParameters(JSONArray array, Scope assignScope, DataType assignDataType) {
		int size = array==null?0:array.size();
		if(size == 0)
			return null;
		
		DeclaredParameter[] parameters = new DeclaredParameter[size];
		for (byte i=0;i<size;i++) {
			parameters[i] = parseDeclaredParameter(array.getJSONObject(i), assignScope, assignDataType);
		}
		return parameters;
	}
}