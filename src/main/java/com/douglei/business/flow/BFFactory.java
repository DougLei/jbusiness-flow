package com.douglei.business.flow;

import com.douglei.business.flow.container.reference.ReferenceContainer;
import com.douglei.business.flow.container.reference.impl.ApplicationReferenceContainer;
import com.douglei.business.flow.executer.BusinessFlow;
import com.douglei.business.flow.resolver.BusinessFlowResolver;
import com.douglei.business.flow.session.SessionPool;

/**
 * 业务流工厂
 * @author DougLei
 */
public class BFFactory {
	private BusinessFlowResolver resolver;
	
	public BFFactory() {
		this(null, new ApplicationReferenceContainer());
	}
	public BFFactory(SessionPool pool) {
		this(pool, new ApplicationReferenceContainer());
	}
	public BFFactory(SessionPool pool, ReferenceContainer referenceContainer) {
		this.resolver = new BusinessFlowResolver(pool, referenceContainer);
	}
	public BusinessFlow build(String bfjson) {
		return resolver.parse(bfjson);
	}
}
