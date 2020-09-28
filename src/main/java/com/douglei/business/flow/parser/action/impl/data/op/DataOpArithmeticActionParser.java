package com.douglei.business.flow.parser.action.impl.data.op;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.action.impl.data.op.arithmetic.ArithmeticType;
import com.douglei.business.flow.executer.action.impl.data.op.arithmetic.DataOpArithmetic;
import com.douglei.business.flow.executer.action.impl.data.op.arithmetic.DataOpArithmeticAction;
import com.douglei.business.flow.parser.ReferenceParser;
import com.douglei.business.flow.parser.action.impl.data.DataOpParser;

/**
 * 
 * @author DougLei
 */
public class DataOpArithmeticActionParser extends DataOpParser{

	@Override
	public String getType() {
		return "data_op_arithmetic";
	}

	@Override
	public Action parse(JSONObject actionJSON, ReferenceParser referenceResolver) {
		JSONArray contents = actionJSON.getJSONArray("content");
		
		short size = (short) contents.size();
		DataOpArithmetic[] group = new DataOpArithmetic[size];
		for(short i=0;i<size;i++) {
			group[i] = parse_(contents.getJSONObject(i), referenceResolver);
		}
		return new DataOpArithmeticAction(group, getResultParameter(actionJSON, null));
	}

	private DataOpArithmetic parse_(JSONObject content, ReferenceParser referenceResolver) {
		DataOpArithmetic dataOpArithmetic = new DataOpArithmetic(ArithmeticType.toValue(content.getString("op")));
		JSONArray groupArray = content.getJSONArray("group");
		if(groupArray == null || groupArray.isEmpty()) {
			dataOpArithmetic.setData(parseData(content, referenceResolver));
		}else {
			byte size = (byte) groupArray.size();
			DataOpArithmetic[] group = new DataOpArithmetic[size];
			for(byte i=0;i<size;i++) {
				group[i] = parse_(groupArray.getJSONObject(i), referenceResolver);
			}
			dataOpArithmetic.setGroup(group);
		}
		return dataOpArithmetic;
	}
}