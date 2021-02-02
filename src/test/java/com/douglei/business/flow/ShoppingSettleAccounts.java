package com.douglei.business.flow;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.douglei.business.flow.db.def.impl.JdbOrmDBSession;
import com.douglei.business.flow.executer.BusinessFlow;
import com.douglei.orm.configuration.Configuration;
import com.douglei.tools.JdkSerializeUtil;
import com.douglei.tools.file.FileUtil;

public class ShoppingSettleAccounts {
	
	@Test
	public void test() {
		JdbOrmDBSession session = new JdbOrmDBSession(new Configuration().buildSessionFactory());
		
		BusinessFlow shoppingBF = new BFFactory().buildByResourceFile("ShoppingSettleAccounts.bf.json");
		
		
		
		JdkSerializeUtil.serialize4File(shoppingBF, new File("C:\\Users\\Administrator.USER-20190410XF\\Desktop\\t.result"));
		
		
		shoppingBF = JdkSerializeUtil.deserialize4File(BusinessFlow.class, new File("C:\\Users\\Administrator.USER-20190410XF\\Desktop\\t.result"));
		
		
		Map<String, Object> inputValueMap = new HashMap<String, Object>();
		inputValueMap.put("shoppingCarId", "1");
		Map<String, Object> result = shoppingBF.execute(inputValueMap, session);
		System.out.println(result);
		
		
		FileUtil.delete(new File("C:\\Users\\Administrator.USER-20190410XF\\Desktop\\t.result"));
	}
}
