package com.douglei.business.flow;

import com.douglei.business.flow.container.reference.ReferenceContainer;
import com.douglei.business.flow.executer.BusinessFlow;
import com.douglei.business.flow.resolver.BusinessFlowResolver;

/**
 * 业务流工厂
 * @author DougLei
 */
public class BFFactory {
	private BusinessFlowResolver resolver;
	
	public BFFactory(ReferenceContainer referenceContainer) {
		this.resolver = new BusinessFlowResolver(referenceContainer);
	}
	
	public BusinessFlow build(String bfjson) {
		return resolver.parse(bfjson);
	}
}
