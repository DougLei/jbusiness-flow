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
public class DataOpArithmeticActionResolver implements ActionResolver{

	@Override
	public String getType() {
		return "data_op_arithmetic";
	}

	@Override
	public Action parse(JSONObject actionJSON, ReferenceResolver referenceResolver) {
		CompareType op = CompareType.toValue(actionJSON.getString("op"));
		Data left = DataResolver.parse(actionJSON.getJSONObject("left"));
		Data right = (op == CompareType.BOOL || op == CompareType.NBOOL)?null:DataResolver.parse(actionJSON.getJSONObject("right"));
		return new DataOpCompareAction(op, left, right);
	}
}




