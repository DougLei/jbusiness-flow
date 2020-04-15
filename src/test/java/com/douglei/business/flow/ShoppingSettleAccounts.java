package com.douglei.business.flow;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.douglei.business.flow.db.def.impl.JdbOrmDBSession;
import com.douglei.business.flow.executer.BusinessFlow;
import com.douglei.orm.configuration.impl.xml.XmlConfiguration;

public class ShoppingSettleAccounts {
	
	@Test
	public void test() {
		JdbOrmDBSession session = new JdbOrmDBSession(new XmlConfiguration().buildSessionFactory());
		
		BusinessFlow shoppingBF = new BFFactory().buildByResourceFile("ShoppingSettleAccounts.bf.json");
		
		Map<String, Object> inputValueMap = new HashMap<String, Object>();
		inputValueMap.put("shoppingCarId", "1");
		Map<String, Object> result = shoppingBF.execute(inputValueMap, session);
		System.out.println(result);
	}
}
