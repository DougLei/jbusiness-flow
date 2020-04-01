package com.douglei.business.flow.resolver.condition;

import com.alibaba.fastjson.JSONArray;
import com.douglei.business.flow.executer.LogicalOP;
import com.douglei.business.flow.executer.condition.ConditionChunk;
import com.douglei.business.flow.executer.condition.ConditionValidator;
import com.douglei.business.flow.resolver.ReferenceResolver;
import com.douglei.tools.utils.CollectionUtil;

/**
 * 条件解析器
 * @author DougLei
 */
public class ConditionResolver {
	private ConditionGroupResolver conditionGroupResolver;
	
	public ConditionResolver(ReferenceResolver referenceResolver) {
		this.conditionGroupResolver = new ConditionGroupResolver(referenceResolver);
	}

	/**
	 * 解析条件(总方法)
	 * @param conditionGroups
	 * @return
	 */
	public ConditionValidator parse(JSONArray conditionGroups) {
		if(CollectionUtil.isEmpty(conditionGroups))
			return ConditionValidator.defaultValidator();
		
		ConditionChunk[] chunks = new ConditionChunk[conditionGroups.size()];
		byte index = 0;
		ConditionChunk chunk;
		for(int i=0;i<conditionGroups.size();i++) {
			chunk = conditionGroupResolver.parse(conditionGroups.getJSONObject(i), i==conditionGroups.size()-1);
			
			if(index > 0 && chunks[index-1].getNextOP() == LogicalOP.AND) {
				chunks[index-1].pushChunk(chunk);
			}else {
				chunks[index++] = chunk;
			}
		}
		return new ConditionValidator(index, chunks);
	}
}