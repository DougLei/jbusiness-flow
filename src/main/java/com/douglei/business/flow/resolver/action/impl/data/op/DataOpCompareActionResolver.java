package com.douglei.business.flow.resolver.action.impl.data.op;

import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.resolver.ReferenceResolver;
import com.douglei.business.flow.resolver.action.ActionResolver;

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
		// TODO Auto-generated method stub
		return null;
	}
	
	class Test{}
}




