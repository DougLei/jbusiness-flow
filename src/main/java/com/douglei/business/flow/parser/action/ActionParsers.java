package com.douglei.business.flow.parser.action;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.parser.ReferenceParser;
import com.douglei.tools.instances.scanner.ClassScanner;
import com.douglei.tools.utils.reflect.ClassLoadUtil;
import com.douglei.tools.utils.reflect.ConstructorUtil;
import com.douglei.tools.utils.reflect.ValidationUtil;

/**
 * 
 * @author DougLei
 */
public class ActionParsers {
	private static final Logger logger = LoggerFactory.getLogger(ActionParsers.class);
	private static final Map<String, ActionParser> MAP = new HashMap<String, ActionParser>(32);
	static {
		ClassScanner cs = new ClassScanner();
		List<String> classPaths = cs.scan(ActionParsers.class.getPackage().getName() + ".impl");
		
		Class<?> clz;
		ActionParser actionResolver;
		for (String cp : classPaths) {
			clz = ClassLoadUtil.loadClass(cp);
			if(Modifier.isAbstract(clz.getModifiers()) || !ValidationUtil.isExtendClass(clz, ActionParser.class)) {
				continue;
			}
			actionResolver= (ActionParser) ConstructorUtil.newInstance(clz);
			MAP.put(actionResolver.getType().toUpperCase(), actionResolver);
		}
		cs.destroy();
	}
	
	/**
	 * 解析action
	 * @param action
	 * @param referenceResolver
	 * @return
	 */
	public static Action parse(JSONObject action, ReferenceParser referenceResolver) {
		ActionParser resolver = MAP.get(action.getString("type").toUpperCase());
		if(logger.isDebugEnabled())
			logger.debug("使用[{}]解析器解析action", resolver.getClass().getName());
		return resolver.parse(action, referenceResolver);
	}
	
	/**
	 * 获取指定type的action解析器
	 * @param type
	 * @return
	 */
	public static ActionParser getActionResolver(String type) {
		ActionParser resolver = MAP.get(type.toUpperCase());
		if(logger.isDebugEnabled())
			logger.debug("使用[{}]解析器解析action", resolver.getClass().getName());
		return resolver;
	}
}
