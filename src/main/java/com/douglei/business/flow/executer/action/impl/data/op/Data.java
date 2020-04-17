package com.douglei.business.flow.executer.action.impl.data.op;

import java.util.HashMap;
import java.util.Map;

import com.douglei.business.flow.db.DBSession;
import com.douglei.business.flow.executer.ParameterContext;
import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.action.impl.func.method.FuncMethodAction;
import com.douglei.business.flow.executer.parameter.DeclaredParameter;
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
	private String format;
	private DataAction action;
	private FuncMethodAction method; // FuncMethodAction
	
	public Data(String format) {
		this.format = format;
	}
	
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

		public DataValue execute(DBSession session, DataValue defaultDataValue) {
			for (Action action : actions) {
				action.execute(session);
			}
			return resultPick.pickValue(defaultDataValue);
		}
	}
	
	private class DataActionResultPick{
		private boolean all;
		private String[] names;
		public DataActionResultPick(boolean all, String[] names) {
			this.all = all;
			this.names = names;
		}
		
		public DataValue pickValue(DataValue defaultDataValue) {
			Map<String, Object> valueMap = null;
			if(all) {
				valueMap = ParameterContext.getValueMap(Scope.LOCAL, names); 
			}else {
				if(names != null) {
					valueMap = new HashMap<String, Object>(names.length);
					DeclaredParameter tmpParameter = DeclaredParameter.newInstance("tmp", Scope.LOCAL);
					for (String name : names) {
						tmpParameter.updateName(name);
						valueMap.put(name, ParameterContext.getValue(tmpParameter));
					}
				}
			}
			
			if(CollectionUtil.isEmpty(valueMap)) {
				return defaultDataValue;
			}else if(valueMap.size() == 1) {
				return new DataValue(valueMap.values().iterator().next());
			}
			return new DataValue(valueMap);
		}
	}
	
	public DataValue getValue(DBSession session) {
		if(value != null) {
			return new DataValue(value).setFormat(format);
		}else if(parameter != null) {
			DeclaredParameter p = ParameterContext.getParameter(parameter);
			return new DataValue(p.getValue(parameter.getOgnlExpression())).setFormat(format);
		}else if(action != null) {
			return action.execute(session, DataValue.NULL_DATA_VALUE);
		}else {
			return method.returnExecuteResult(session, DataValue.NULL_DATA_VALUE);
		}
	}
}
