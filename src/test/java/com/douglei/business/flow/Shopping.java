package com.douglei.business.flow;

import org.junit.Test;

import com.douglei.business.flow.db.def.impl.JdbOrmDBSessionFactory;
import com.douglei.orm.configuration.impl.xml.XmlConfiguration;

public class Shopping {
	
	@Test
	public void test() {
		BFFactory bf = new BFFactory(new JdbOrmDBSessionFactory(new XmlConfiguration().buildSessionFactory()));
		System.out.println(bf);
		
		
		
	}
}
