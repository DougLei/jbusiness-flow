package com.douglei.business.flow;

import com.douglei.business.flow.container.reference.ReferenceContainer;
import com.douglei.business.flow.container.reference.impl.ApplicationReferenceContainer;
import com.douglei.business.flow.db.DBSessionFactory;
import com.douglei.business.flow.executer.BusinessFlow;
import com.douglei.business.flow.resolver.BusinessFlowResolver;

/**
 * 业务流工厂
 * @author DougLei
 */
public class BFFactory {
	private BusinessFlowResolver resolver;
	
	public BFFactory() {
		this(new ApplicationReferenceContainer(), null);
	}
	public BFFactory(ReferenceContainer referenceContainer) {
		this(referenceContainer, null);
	}
	public BFFactory(ReferenceContainer referenceContainer, DBSessionFactory dbSessionFactory) {
		this.resolver = new BusinessFlowResolver(referenceContainer, dbSessionFactory);
	}
	
	public BusinessFlow build(String bfjson) {
		return resolver.parse(bfjson);
	}
}
