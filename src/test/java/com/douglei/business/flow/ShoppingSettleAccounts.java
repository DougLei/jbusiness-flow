package com.douglei.business.flow;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.douglei.business.flow.db.def.impl.JdbOrmDBSession;
import com.douglei.business.flow.executer.BusinessFlow;
import com.douglei.orm.configuration.Configuration;
import com.douglei.tools.utils.FileUtil;
import com.douglei.tools.utils.serialize.JdkSerializeProcessor;

public class ShoppingSettleAccounts {
	
	@Test
	public void test() {
		JdbOrmDBSession session = new JdbOrmDBSession(new Configuration().buildSessionFactory());
		
		BusinessFlow shoppingBF = new BFFactory().buildByResourceFile("ShoppingSettleAccounts.bf.json");
		
		
		
		JdkSerializeProcessor.serialize2File(shoppingBF, "C:\\Users\\Administrator.USER-20190410XF\\Desktop\\t.result");
		
		
		shoppingBF = JdkSerializeProcessor.deserializeFromFile(BusinessFlow.class, "C:\\Users\\Administrator.USER-20190410XF\\Desktop\\t.result");
		
		
		Map<String, Object> inputValueMap = new HashMap<String, Object>();
		inputValueMap.put("shoppingCarId", "1");
		Map<String, Object> result = shoppingBF.execute(inputValueMap, session);
		System.out.println(result);
		
		
		FileUtil.delete(new File("C:\\Users\\Administrator.USER-20190410XF\\Desktop\\t.result"));
	}
}
