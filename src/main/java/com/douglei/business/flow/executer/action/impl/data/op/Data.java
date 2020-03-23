package com.douglei.business.flow.executer.action.impl.data.op;

import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.parameter.Parameter;

/**
 * 
 * @author DougLei
 */
public class Data {
	private Object defaultValue;
	private Parameter parameter;
	private DataAction action;
	private Action method; // FuncMethodAction
	
	public void setDefaultValue(Object defaultValue) {
		this.defaultValue = defaultValue;
	}
	public void setParameter(Parameter parameter) {
		this.parameter = parameter;
	}
	public void setAction(Action[] actions, boolean resultPick_all, String[] resultPick_names) {
		this.action = new DataAction(actions, resultPick_all, resultPick_names);
	}
	public void setMethod(Action method) {
		this.method = method;
	}

	private class DataAction {
		private Action[] actions;
		private DataActionResultPick resultPick;
		
		public DataAction(Action[] actions, boolean resultPick_all, String[] resultPick_names) {
			this.actions = actions;
			this.resultPick = new DataActionResultPick(resultPick_all, resultPick_names);
		}
	}
	
	private class DataActionResultPick{
		private boolean all;
		private String[] names;
		public DataActionResultPick(boolean all, String[] names) {
			this.all = all;
			this.names = names;
		}
		
	}

	public DataValue getValue() {
		// TODO 获取值
		return null;
	}
}
