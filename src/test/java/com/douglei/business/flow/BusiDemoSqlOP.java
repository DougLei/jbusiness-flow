package com.douglei.business.flow;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.db.def.impl.JdbOrmDBSession;
import com.douglei.business.flow.executer.BusinessFlow;
import com.douglei.orm.configuration.Configuration;

public class BusiDemoSqlOP {
	
	@Test
	public void opTest() {
		BusinessFlow opBF = new BFFactory().buildByResourceFile("BusiDemoSqlOP.bf.json");
		
		Map<String, Object> inputValueMap = new HashMap<String, Object>();
		inputValueMap.put("productName", "雪碧");
		inputValueMap.put("productPrice", 3);
		
		Map<String, Object> result = opBF.execute(inputValueMap, new JdbOrmDBSession(new Configuration().buildSessionFactory()));
		System.out.println(JSONObject.toJSONString(result));
	}
}
