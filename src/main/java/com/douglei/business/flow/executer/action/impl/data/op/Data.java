package com.douglei.business.flow.executer.action.impl.data.op;

import com.douglei.business.flow.executer.Parameter;
import com.douglei.business.flow.executer.action.Action;

/**
 * 
 * @author DougLei
 */
public class Data {
	private Object value;
	private Parameter parameter;
	private DataAction action;
	private Action method; // FuncMethodAction
	
	
	public Object getValue() {
		return value;
	}
	public Parameter getParameter() {
		return parameter;
	}
	public DataAction getAction() {
		return action;
	}
	public Action getMethod() {
		return method;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public void setParameter(Parameter parameter) {
		this.parameter = parameter;
	}
	public void setAction(Action[] actions, boolean pickAll, String[] names) {
		this.action = new DataAction(actions, pickAll, names);
	}
	public void setMethod(Action method) {
		this.method = method;
	}


	private class DataAction {
		private Action[] actions;
		private boolean pickAll;
		private String[] names;
		
		public DataAction(Action[] actions, boolean pickAll, String[] names) {
			this.actions = actions;
			this.pickAll = pickAll;
			this.names = names;
		}
	}
}
