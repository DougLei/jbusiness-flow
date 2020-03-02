package com.douglei.business.flow.resolver;

import com.alibaba.fastjson.JSONArray;
import com.douglei.business.flow.BFConfiguration;

/**
 * 
 * @author DougLei
 */
public class SqlResolver {
	private BFConfiguration configuration;
	private SqlResolver(BFConfiguration configuration) {
		this.configuration = configuration;
	}
	
	public static SqlResolver instance(BFConfiguration configuration, JSONArray array) {
		return null;
	}
}
