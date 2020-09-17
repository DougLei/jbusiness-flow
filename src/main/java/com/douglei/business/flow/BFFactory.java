package com.douglei.business.flow;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.douglei.business.flow.container.reference.ReferenceContainer;
import com.douglei.business.flow.container.reference.impl.ApplicationReferenceContainer;
import com.douglei.business.flow.executer.BusinessFlow;
import com.douglei.business.flow.parser.BusinessFlowParser;
import com.douglei.tools.instances.file.reader.FileBufferedReader;

/**
 * 业务流工厂
 * @author DougLei
 */
public class BFFactory {
	private BusinessFlowParser parser;
	
	public BFFactory() {
		this(null);
	}
	public BFFactory(ReferenceContainer referenceContainer) {
		if(referenceContainer == null)
			referenceContainer = new ApplicationReferenceContainer();
		this.parser = new BusinessFlowParser(referenceContainer);
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
		FileBufferedReader reader = new FileBufferedReader(in);
		return build(reader.readAll(5000).trim());
	}
	
	/**
	 * 根据json字符串, 构建业务流对象
	 * @param json
	 * @return
	 */
	public BusinessFlow build(String json) {
		return parser.parse(json);
	}
}
