package com.douglei.business.flow.resolver.action;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.resolver.ReferenceResolver;
import com.douglei.tools.instances.scanner.ClassScanner;
import com.douglei.tools.utils.reflect.ClassLoadUtil;
import com.douglei.tools.utils.reflect.ConstructorUtil;
import com.douglei.tools.utils.reflect.ValidationUtil;

/**
 * 
 * @author DougLei
 */
public class ActionResolvers {
	private static final Map<String, ActionResolver> MAP = new HashMap<String, ActionResolver>(32);
	static {
		ClassScanner cs = new ClassScanner();
		List<String> classPaths = cs.scan(ActionResolvers.class.getPackage().getName() + ".impl");
		
		Class<?> clz;
		ActionResolver actionResolver;
		for (String cp : classPaths) {
			clz = ClassLoadUtil.loadClass(cp);
			if(Modifier.isAbstract(clz.getModifiers()) || !ValidationUtil.isExtendClass(clz, ActionResolver.class)) {
				continue;
			}
			actionResolver= (ActionResolver) ConstructorUtil.newInstance(clz);
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
	public static Action parse(JSONObject action, ReferenceResolver referenceResolver) {
		return MAP.get(action.getString("type").toUpperCase()).parse(action, referenceResolver);
	}
	
	/**
	 * 获取指定type的action解析器
	 * @param type
	 * @return
	 */
	public static ActionResolver getActionResolver(String type) {
		return MAP.get(type.toUpperCase());
	}
}
