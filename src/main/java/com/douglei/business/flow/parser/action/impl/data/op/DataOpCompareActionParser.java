package com.douglei.business.flow.parser.action.impl.data.op;

import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.executer.DataType;
import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.action.impl.data.op.Data;
import com.douglei.business.flow.executer.action.impl.data.op.compare.CompareType;
import com.douglei.business.flow.executer.action.impl.data.op.compare.DataOpCompareAction;
import com.douglei.business.flow.parser.ReferenceParser;
import com.douglei.business.flow.parser.action.impl.data.DataOpParser;

/**
 * 
 * @author DougLei
 */
public class DataOpCompareActionParser extends DataOpParser{

	@Override
	public String getType() {
		return "data_op_compare";
	}

	@Override
	public Action parse(JSONObject actionJSON, ReferenceParser referenceResolver) {
		JSONObject content = actionJSON.getJSONObject("content");
		CompareType op = CompareType.toValue(content.getString("op"));
		Data left = parseData(content.getJSONObject("left"), referenceResolver);
		Data right = (op == CompareType.BOOL || op == CompareType.NBOOL)?null:parseData(content.getJSONObject("right"), referenceResolver);
		return new DataOpCompareAction(op, left, right, getResultParameter(actionJSON, DataType.BOOLEAN));
	}
}




