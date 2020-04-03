package com.douglei.business.flow.resolver.condition;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.executer.LogicalOP;
import com.douglei.business.flow.executer.condition.Condition;
import com.douglei.business.flow.executer.condition.ConditionChunk;
import com.douglei.business.flow.resolver.ReferenceResolver;
import com.douglei.business.flow.resolver.action.ActionResolvers;
import com.douglei.tools.utils.CollectionUtil;

/**
 * 
 * @author DougLei
 */
class ConditionGroupResolver {
	private static final String ACTION_TYPE = "data_op_compare";
	
	private ReferenceResolver referenceResolver;
	public ConditionGroupResolver(ReferenceResolver referenceResolver) {
		this.referenceResolver = referenceResolver;
	}
	
	/**
	 * 解析conditionGroups, 获取ConditionChunk数组
	 * @param conditionGroups
	 * @return
	 */
	ConditionChunk[] parse(JSONArray conditionGroups) {
		ConditionChunk[] chunks = new ConditionChunk[conditionGroups.size()];
		byte index = 0;
		ConditionChunk chunk;
		for(int i=0;i<conditionGroups.size();i++) {
			chunk = parse(conditionGroups.getJSONObject(i), i==conditionGroups.size()-1);
			
			if(index > 0 && chunks[index-1].getNextOP() == LogicalOP.AND) {
				chunks[index-1].appendChunk(chunk);
			}else {
				chunks[index++] = chunk;
			}
		}
		return chunks;
	}
	
	public ConditionChunk parse(JSONObject conditionGroup, boolean isEnd) {
		ConditionChunk chunk = new ConditionChunk(conditionGroup.getBooleanValue("inverse"), isEnd?null:LogicalOP.toValue(conditionGroup.getByteValue("op")));
		parseConditionGroups(conditionGroup, chunk);
		parseConditions(conditionGroup.getJSONArray("conditions"), chunk);
		return chunk;
	}
	
	private void parseConditionGroups(JSONObject conditionGroup, ConditionChunk topChunk) {
		JSONArray conditionGroups = conditionGroup.getJSONArray("conditionGroups");
		if(CollectionUtil.isEmpty(conditionGroups))
			return;
		
		ConditionChunk[] chunks = parse(conditionGroups);
		topChunk.appendChunk(chunks, LogicalOP.toValue(conditionGroup.getByteValue("cgcop")));
	}
	
	private void parseConditions(JSONArray conditions, ConditionChunk topChunk) {
		if(CollectionUtil.unEmpty(conditions)) {
			JSONObject condition;
			for(int i=0;i<conditions.size();i++) {
				condition = conditions.getJSONObject(i);
				topChunk.appendChunk(
					new Condition(
							condition.getBooleanValue("inverse"), 
							i==conditions.size()-1?null:LogicalOP.toValue(condition.getByteValue("op")),
							ActionResolvers.getActionResolver(ACTION_TYPE).parse(condition, referenceResolver)));
			}
		}
	}
}