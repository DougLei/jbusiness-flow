package com.douglei.business.flow.executer.action.impl.data.op;

import java.util.HashMap;
import java.util.Map;

import com.douglei.business.flow.executer.ParameterContext;
import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.action.impl.func.method.FuncMethodAction;
import com.douglei.business.flow.executer.parameter.Parameter;
import com.douglei.business.flow.executer.parameter.Scope;
import com.douglei.tools.utils.CollectionUtil;

/**
 * 
 * @author DougLei
 */
public class Data {
	private Object value;
	private Parameter parameter;
	private DataAction action;
	private FuncMethodAction method; // FuncMethodAction
	
	public void setValue(Object value) {
		this.value = value;
	}
	public void setParameter(Parameter parameter) {
		this.parameter = parameter;
	}
	public void setAction(Action[] actions, boolean resultPick_all, String[] resultPick_names) {
		this.action = new DataAction(actions, resultPick_all, resultPick_names);
	}
	public void setMethod(Action method) {
		this.method = (FuncMethodAction) method;
	}

	private class DataAction {
		private Action[] actions;
		private DataActionResultPick resultPick;
		
		public DataAction(Action[] actions, boolean resultPick_all, String[] resultPick_names) {
			this.actions = actions;
			this.resultPick = new DataActionResultPick(resultPick_all, resultPick_names);
		}

		public DataValue execute() {
			for (Action action : actions) {
				action.execute();
			}
			return resultPick.pickValue();
		}
	}
	
	private class DataActionResultPick{
		private boolean all;
		private String[] names;
		public DataActionResultPick(boolean all, String[] names) {
			this.all = all;
			this.names = names;
		}
		
		public DataValue pickValue() {
			Map<String, Object> valueMap = null;
			if(all) {
				valueMap = ParameterContext.getValueMap(Scope.LOCAL, names); 
			}else {
				if(names != null) {
					valueMap = new HashMap<String, Object>(names.length);
					Parameter tmpParameter = Parameter.newInstance("tmp", Scope.LOCAL);
					for (String name : names) {
						tmpParameter.updateName(name);
						valueMap.put(name, ParameterContext.getValue(tmpParameter));
					}
				}
			}
			
			if(CollectionUtil.isEmpty(valueMap)) {
				return new DataValue();
			}else if(valueMap.size() == 1) {
				return new DataValue(valueMap.values().iterator().next());
			}
			return new DataValue(valueMap);
		}
	}
	
	public DataValue getValue() {
		if(value != null) {
			return new DataValue(value);
		}else if(parameter != null) {
			return new DataValue(ParameterContext.getParameter(parameter));
		}else if(action != null) {
			return action.execute();
		}else {
			return method.returnExecuteResult();
		}
	}
}
