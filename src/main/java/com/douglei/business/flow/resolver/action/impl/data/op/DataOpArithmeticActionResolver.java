package com.douglei.business.flow.resolver.action.impl.data.op;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.action.impl.data.op.arithmetic.ArithmeticType;
import com.douglei.business.flow.executer.action.impl.data.op.arithmetic.DataOpArithmetic;
import com.douglei.business.flow.executer.action.impl.data.op.arithmetic.DataOpArithmeticAction;
import com.douglei.business.flow.resolver.ReferenceResolver;
import com.douglei.business.flow.resolver.action.ActionResolver;
import com.douglei.business.flow.resolver.action.impl.data.DataResolver;
import com.douglei.tools.utils.CollectionUtil;

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
		JSONArray contents = actionJSON.getJSONArray("content");
		
		byte size = (byte) contents.size();
		DataOpArithmetic[] group = new DataOpArithmetic[size];
		for(byte i=0;i<size;i++) {
			group[i] = parse_(contents.getJSONObject(i), referenceResolver);
		}
		return new DataOpArithmeticAction(group);
	}

	private DataOpArithmetic parse_(JSONObject content, ReferenceResolver referenceResolver) {
		DataOpArithmetic dataOpArithmetic = new DataOpArithmetic(ArithmeticType.toValue(content.getString("op")));
		JSONArray groupArray = content.getJSONArray("group");
		if(CollectionUtil.isEmpty(groupArray)) {
			dataOpArithmetic.setData(DataResolver.parse(content, referenceResolver));
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