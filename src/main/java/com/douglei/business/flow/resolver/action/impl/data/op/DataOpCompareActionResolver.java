package com.douglei.business.flow.resolver.action.impl.data.op;

import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.action.impl.data.op.Data;
import com.douglei.business.flow.executer.action.impl.data.op.compare.CompareType;
import com.douglei.business.flow.executer.action.impl.data.op.compare.DataOpCompareAction;
import com.douglei.business.flow.resolver.ReferenceResolver;
import com.douglei.business.flow.resolver.action.ActionResolver;
import com.douglei.business.flow.resolver.action.impl.data.DataResolver;

/**
 * 
 * @author DougLei
 */
public class DataOpCompareActionResolver implements ActionResolver{

	@Override
	public String getType() {
		return "data_op_compare";
	}

	@Override
	public Action parse(JSONObject actionJSON, ReferenceResolver referenceResolver) {
		JSONObject content = actionJSON.getJSONObject("content");
		
		CompareType op = CompareType.toValue(content.getString("op"));
		Data left = DataResolver.parse(content.getJSONObject("left"), referenceResolver);
		Data right = (op == CompareType.BOOL || op == CompareType.NBOOL)?null:DataResolver.parse(content.getJSONObject("right"), referenceResolver);
		
		// RESULT
		return new DataOpCompareAction(op, left, right);
	}
}




