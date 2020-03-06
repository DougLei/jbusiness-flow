package com.douglei.business.flow.executer.action;

/**
 * 
 * @author DougLei
 */
public abstract class Action {
	protected Content content;
	protected Result result;
	
	/**
	 * 
	 * @return
	 */
	public abstract Object execute();
}
