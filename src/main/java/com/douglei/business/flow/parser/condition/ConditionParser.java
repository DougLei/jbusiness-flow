package com.douglei.business.flow.parser.condition;

import com.alibaba.fastjson.JSONArray;
import com.douglei.business.flow.executer.condition.ConditionValidator;
import com.douglei.business.flow.parser.ReferenceParser;

/**
 * 条件解析器
 * @author DougLei
 */
public class ConditionParser {
	private static final ThreadLocal<ConditionGroupParser> CONTEXT = new ThreadLocal<ConditionGroupParser>();
	
	/**
	 * 解析条件
	 * @param conditionGroups
	 * @param referenceResolver
	 * @return
	 */
	public static ConditionValidator parse(JSONArray conditionGroups, ReferenceParser referenceResolver) {
		if(conditionGroups == null || conditionGroups.isEmpty())
			return ConditionValidator.defaultValidator();
		
		ConditionGroupParser resolver = CONTEXT.get();
		if(resolver == null) {
			resolver = new ConditionGroupParser(referenceResolver);
			CONTEXT.set(resolver);
		}else {
			resolver.updateReferenceResolver(referenceResolver);
		}
		return resolver.parse(conditionGroups);
	}
}
