package com.douglei.business.flow.resolver.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.douglei.business.flow.core.action.Action;
import com.douglei.tools.instances.scanner.ClassScanner;
import com.douglei.tools.utils.reflect.ConstructorUtil;

/**
 * 
 * @author DougLei
 */
public class ActionResolvers {
	private static final Map<String, ActionResolver> map = new HashMap<String, ActionResolver>(32);
	static {
		ClassScanner cs = new ClassScanner();
		List<String> classPaths = cs.scan(ActionResolvers.class.getPackage().getName() + ".impl");
		
		ActionResolver actionResolver = null;
		for (String cp : classPaths) {
			actionResolver= (ActionResolver) ConstructorUtil.newInstance(cp);
			map.put(actionResolver.getType(), actionResolver);
		}
		cs.destroy();
	}
	
	public static Action parse(JSONObject action) {
		return map.get(action.getString("type")).parse(action);
	}
}
