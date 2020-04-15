package com.douglei.business.flow;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.douglei.business.flow.db.def.impl.JdbOrmDBSession;
import com.douglei.business.flow.executer.BusinessFlow;
import com.douglei.orm.configuration.impl.xml.XmlConfiguration;
import com.douglei.orm.sessionfactory.SessionFactory;

public class ShoppingSettleAccounts {
	
	@Test
	public void test() {
		SessionFactory sf = new XmlConfiguration().buildSessionFactory();
		BFFactory bf = new BFFactory();
		BusinessFlow shoppingBF = bf.buildByResourceFile("ShoppingSettleAccounts.bf.json");
		Map<String, Object> inputValueMap = new HashMap<String, Object>();
		inputValueMap.put("shoppingCarId", "1");
		Map<String, Object> result = shoppingBF.execute(inputValueMap, new JdbOrmDBSession(sf));
		System.out.println(result);
	}
}
