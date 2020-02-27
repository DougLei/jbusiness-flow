package com.douglei.business.flow;

import com.douglei.business.flow.core.BusinessFlow;
import com.douglei.business.flow.resolver.BusinessFlowResolver;

/**
 * 业务流工厂
 * @author DougLei
 */
public class BFFactory {
	private BusinessFlowResolver resolver;
	private BFContainer container;
	
	public BFFactory(BFConfiguration configuration, BFContainer container) {
		this.resolver = new BusinessFlowResolver(configuration);
		this.container = container;
	}
	
	// TODO 后续优化, 对业务流容器操作的各种验证
	
	public BusinessFlow build(String bfjson) {
		BusinessFlow bf = resolver.parse(bfjson);
		container.put(bf);
		return bf;
	}
	
	public BusinessFlow get(String name, String version) {
		return container.get(name, version);
	}
}
