package com.douglei.business.flow.core.action;

/**
 * 
 * @author DougLei
 */
public abstract class Action {
	protected String type;
	protected Content content;
	protected Result result;
	
	
	
	/**
	 * 执行
	 */
	public abstract void execute();
}
