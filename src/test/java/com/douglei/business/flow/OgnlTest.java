package com.douglei.business.flow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.douglei.tools.ognl.OgnlHandler;

public class OgnlTest {
	
	@Test
	public void test() {
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<String> list = new ArrayList<String>();
		list.add("1");
		map.put("list", list);
		
		List<String> list2 = new ArrayList<String>();
		list2.add("2");
		map.put("list2", list2);
		
		map.put("str", " ");
		
		System.out.println(OgnlHandler.getSingleton().getObjectValue("list.equals(list2)", map));;
		System.out.println(OgnlHandler.getSingleton().getObjectValue("str == \" \"", map));;
	}
}
