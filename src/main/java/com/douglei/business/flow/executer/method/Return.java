package com.douglei.business.flow.executer.method;

import java.util.HashMap;
import java.util.Map;

import com.douglei.business.flow.executer.parameter.Parameter;

/**
 * 
 * @author DougLei
 */
public class Return {
	// all=true时,  排除指定name的参数不返回, 剩下的参数都返回(可为空)
	// all=false时, 返回指定name的参数(可为空)
	private boolean all;
	private String[] names;
	
	public Return(boolean all, String[] names) {
		this.all = all;
		this.names = names;
	}

	/**
	 * 过滤出需要的参数并返回
	 * @param parameterMap
	 * @return
	 */
	public Map<String, Parameter> filter(Map<String, Parameter> parameterMap) {
		if(all) {
			if(names != null && names.length > 0) {
				for (String name : names) {
					parameterMap.remove(name);
				}
			}
			return parameterMap;
		}else {
			if(names != null && names.length > 0) {
				Map<String, Parameter> returnParameterMap = new HashMap<String, Parameter>(names.length);
				for (String name : names) {
					returnParameterMap.put(name, parameterMap.get(name));
				}
				return returnParameterMap;
			}
			return null;
		}
	}
}
