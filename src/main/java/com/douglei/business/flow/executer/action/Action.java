package com.douglei.business.flow.executer.action;

/**
 * 
 * @author DougLei
 */
public abstract class Action {
	protected Content content;
	protected Result result;
	
	/**
	 * 执行
	 */
	public abstract void execute();
}
