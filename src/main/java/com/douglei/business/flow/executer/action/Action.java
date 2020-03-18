package com.douglei.business.flow.executer.action;

import com.douglei.business.flow.executer.parameter.Parameter;

/**
 * 
 * @author DougLei
 */
public abstract class Action {
	protected Parameter result;
	
	/**
	 * 
	 * @return
	 */
	public abstract Object execute();
}
