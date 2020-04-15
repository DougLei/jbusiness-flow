package com.douglei.business.flow;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.douglei.business.flow.db.def.impl.JdbOrmDBSessionFactory;
import com.douglei.business.flow.executer.BusinessFlow;
import com.douglei.orm.configuration.impl.xml.XmlConfiguration;

public class ShoppingSettleAccounts {
	
	@Test
	public void test() {
		BFFactory bf = new BFFactory(new JdbOrmDBSessionFactory(new XmlConfiguration().buildSessionFactory()));
		BusinessFlow shoppingBF = bf.buildByResourceFile("ShoppingSettleAccounts.bf.json");
		
		Map<String, Object> inputValueMap = new HashMap<String, Object>();
		inputValueMap.put("shoppingCarId", "1");
		
		Map<String, Object> result = shoppingBF.execute(inputValueMap );
		
		System.out.println(result);
		
	}
}
