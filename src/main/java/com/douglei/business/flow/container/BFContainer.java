package com.douglei.business.flow.container;

import com.douglei.business.flow.core.BusinessFlow;

/**
 * 业务流容器
 * @author DougLei
 */
public interface BFContainer {
	
	/**
	 * 保存业务流
	 * @param bf
	 * @return bf
	 */
	BusinessFlow put(BusinessFlow bf);
	
	/**
	 * 获取业务流
	 * @param name 名称
	 * @param version 版本
	 * @return
	 */
	BusinessFlow get(String name, String version);
}
