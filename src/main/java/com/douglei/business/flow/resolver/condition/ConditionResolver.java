package com.douglei.business.flow.resolver.condition;

import com.alibaba.fastjson.JSONArray;
import com.douglei.business.flow.executer.condition.ConditionValidator;
import com.douglei.business.flow.resolver.ReferenceResolver;
import com.douglei.tools.utils.CollectionUtil;

/**
 * 条件解析器
 * @author DougLei
 */
public class ConditionResolver {
	private static final ThreadLocal<ConditionGroupResolver> CONTEXT = new ThreadLocal<ConditionGroupResolver>();
	
	/**
	 * 解析条件
	 * @param conditionGroups
	 * @param referenceResolver
	 * @return
	 */
	public static ConditionValidator parse(JSONArray conditionGroups, ReferenceResolver referenceResolver) {
		if(CollectionUtil.isEmpty(conditionGroups))
			return ConditionValidator.defaultValidator();
		
		ConditionGroupResolver resolver = CONTEXT.get();
		if(resolver == null) {
			resolver = new ConditionGroupResolver(referenceResolver);
			CONTEXT.set(resolver);
		}else {
			resolver.updateReferenceResolver(referenceResolver);
		}
		return resolver.parse(conditionGroups);
	}
}
