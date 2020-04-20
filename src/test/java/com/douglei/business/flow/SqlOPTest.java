package com.douglei.business.flow;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.douglei.business.flow.db.def.impl.JdbOrmDBSession;
import com.douglei.business.flow.executer.BusinessFlow;
import com.douglei.orm.configuration.impl.xml.XmlConfiguration;

public class SqlOPTest {
	
	@Test
	public void opTest() {
		JdbOrmDBSession session = new JdbOrmDBSession(new XmlConfiguration().buildSessionFactory());
		BusinessFlow opBF = new BFFactory().buildByResourceFile("SqlOP.bf.json");
		
		Map<String, Object> inputValueMap = new HashMap<String, Object>();
		inputValueMap.put("shoppingCarId", "testID");
		inputValueMap.put("productName", "测试");
		inputValueMap.put("productType", 5);
		inputValueMap.put("productPrice", 239.8);
//		inputValueMap.put("productCount", 20);
		
		Map<String, Object> result = opBF.execute(inputValueMap, session);
		System.out.println(result);
	}
}
