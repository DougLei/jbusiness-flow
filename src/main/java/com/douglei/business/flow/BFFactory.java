package com.douglei.business.flow;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import com.douglei.business.flow.container.reference.ReferenceContainer;
import com.douglei.business.flow.container.reference.impl.ApplicationReferenceContainer;
import com.douglei.business.flow.executer.BusinessFlow;
import com.douglei.business.flow.resolver.BusinessFlowResolver;
import com.douglei.tools.instances.file.resources.reader.ResourcesReader;

/**
 * 业务流工厂
 * @author DougLei
 */
public class BFFactory {
	private BusinessFlowResolver resolver;
	
	public BFFactory() {
		this(null);
	}
	public BFFactory(ReferenceContainer referenceContainer) {
		if(referenceContainer == null)
			referenceContainer = new ApplicationReferenceContainer();
		this.resolver = new BusinessFlowResolver(referenceContainer);
	}
	
	/**
	 * 根据指定的文件, 构建业务流对象
	 * @param file
	 * @return
	 */
	public BusinessFlow buildByFile(String file) {
		File f = new File(file);
		if(!f.exists())
			return null;
		try {
			return buildByInputStream(new FileInputStream(f));
		} catch (FileNotFoundException e) {
			return null;
		}
	}
	
	/**
	 * 根据指定的资源文件, 构建业务流对象
	 * @param resourceFile
	 * @return
	 */
	public BusinessFlow buildByResourceFile(String resourceFile) {
		return buildByInputStream(BFFactory.class.getClassLoader().getResourceAsStream(resourceFile));
	}
	
	/**
	 * 根据流, 构建业务流对象
	 * @param in
	 * @return
	 */
	public BusinessFlow buildByInputStream(InputStream in) {
		ResourcesReader reader = new ResourcesReader(in, StandardCharsets.UTF_8);
		return build(reader.readAll(3000).toString());
	}
	
	/**
	 * 根据json字符串, 构建业务流对象
	 * @param json
	 * @return
	 */
	public BusinessFlow build(String json) {
		return resolver.parse(json);
	}
}
