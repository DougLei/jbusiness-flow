package com.douglei.business.flow;

import com.douglei.business.flow.executer.BusinessFlow;
import com.douglei.business.flow.resolver.BusinessFlowResolver;

/**
 * 业务流工厂
 * @author DougLei
 */
public class BFFactory {
	private BusinessFlowResolver resolver;
	
	public BFFactory(BFConfiguration configuration) {
		this.resolver = new BusinessFlowResolver(configuration);
	}
	
	public BusinessFlow build(String bfjson) {
		BusinessFlow bf = resolver.parse(bfjson);
		return bf;
	}
}
