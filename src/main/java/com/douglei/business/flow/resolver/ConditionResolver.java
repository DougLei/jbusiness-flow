package com.douglei.business.flow.resolver;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.executer.LogicalOP;
import com.douglei.business.flow.executer.condition.Condition;
import com.douglei.business.flow.executer.condition.ConditionHandler;
import com.douglei.business.flow.executer.condition.ConditionValidator;
import com.douglei.business.flow.resolver.action.ActionResolvers;
import com.douglei.tools.utils.CollectionUtil;

/**
 * 
 * @author DougLei
 */
public class ConditionResolver {
	private static final String ACTION_TYPE = "data_op_compare";
	
	/**
	 * 解析条件组
	 * @param conditionGroups
	 * @param referenceResolver
	 * @return
	 */
	public static ConditionValidator parse(JSONArray conditionGroups, ReferenceResolver referenceResolver) {
		if(CollectionUtil.isEmpty(conditionGroups)) {
			return ConditionValidator.defaultValidator();
		}
		
		ConditionHandler handler = new ConditionHandler();
		pushConditionGroups(handler, referenceResolver, conditionGroups);
		return handler.getConditionValidator();
	}
	
	private static boolean pushConditionGroups(ConditionHandler handler, ReferenceResolver referenceResolver, JSONArray conditionGroups) {
		if(CollectionUtil.unEmpty(conditionGroups)) {
			JSONObject conditionGroup;
			for(int i=0;i<conditionGroups.size();i++) {
				conditionGroup = conditionGroups.getJSONObject(i);
				
				if(conditionGroup.getBooleanValue("inverse"))
					stack.pushLogicalOP(LogicalOP.NOT);
				stack.pushLogicalOP(LogicalOP.LEFT_PARENTHESES);
				pushConditions(stack, referenceResolver, 
						pushConditionGroups(stack, referenceResolver, conditionGroup.getJSONArray("conditionGroups"))?LogicalOP.toValue(conditionGroup.getByteValue("cgcop")):null, 
								conditionGroup.getJSONArray("conditions"));
				stack.pushLogicalOP(LogicalOP.RIGHT_PARENTHESES);
				if(i<conditionGroups.size()-1)
					stack.pushLogicalOP(LogicalOP.toValue(conditionGroup.getByteValue("op")));
			}
			return true;
		}
		return false;
	}
	
	private static void pushConditions(ConditionHandler handler, ReferenceResolver referenceResolver, JSONArray conditions) {
		if(CollectionUtil.unEmpty(conditions)) {
			JSONObject condition;
			for(int i=0;i<conditions.size();i++) {
				condition = conditions.getJSONObject(i);
				
				stack.pushCondition(new Condition(
						condition.getBooleanValue("inverse"), 
						ActionResolvers.getActionResolver(ACTION_TYPE).parse(condition, referenceResolver),
						(i<conditions.size()-1)?LogicalOP.toValue(condition.getByteValue("op")):null
						));
			}
		}
	}
}