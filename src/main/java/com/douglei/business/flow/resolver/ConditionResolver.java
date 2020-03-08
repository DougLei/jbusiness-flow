package com.douglei.business.flow.resolver;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.executer.Condition;
import com.douglei.business.flow.executer.ConditionGroup;
import com.douglei.business.flow.resolver.action.ActionResolvers;

/**
 * 
 * @author DougLei
 */
public class ConditionResolver {
	private static final ConditionGroup[] EMPTY_CONDITION_GROUPS = new ConditionGroup[0]; // 空的条件组数组
	private static final Condition[] EMPTY_CONDITIONS = new Condition[0]; // 空的条件数组
	private static final String ACTION_TYPE = "data_op_compare";
	
	/**
	 * 
	 * @param array
	 * @param referenceResolver) {

	 * @return
	 */
	public static ConditionGroup[] parse(JSONArray array, ReferenceResolver referenceResolver) {
		int size = array==null?0:array.size();
		if(size == 0) {
			return EMPTY_CONDITION_GROUPS;
		}
		
		ConditionGroup[] groups = new ConditionGroup[size];
		JSONObject json;
		for(byte i=0;i<size;i++) {
			json = array.getJSONObject(i);
			groups[i] = new ConditionGroup(parseConditions(json.getJSONArray("conditions"), referenceResolver), json.getByteValue("op"));
		}
		return groups;
	}

	private static Condition[] parseConditions(JSONArray array, ReferenceResolver referenceResolver) {
		int size = array==null?0: array.size();
		if(size == 0) {
			return EMPTY_CONDITIONS;
		}
		
		Condition[] conditions = new Condition[size];
		JSONObject json;
		for(byte i=0;i<size;i++) {
			json = array.getJSONObject(i);
			conditions[i] = new Condition(ActionResolvers.getActionResolver(ACTION_TYPE).parse(json, referenceResolver), json.getByteValue("op"));
		}
		return conditions;
	}
}
