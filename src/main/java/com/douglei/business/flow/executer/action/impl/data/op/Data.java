package com.douglei.business.flow.executer.action.impl.data.op;

import java.util.HashMap;
import java.util.Map;

import com.douglei.business.flow.db.DBSession;
import com.douglei.business.flow.executer.DataType;
import com.douglei.business.flow.executer.ParameterContext;
import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.action.impl.func.method.FuncMethodAction;
import com.douglei.business.flow.executer.parameter.ActualParameter;
import com.douglei.business.flow.executer.parameter.Parameter;
import com.douglei.business.flow.executer.parameter.Scope;

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
	
	public DataValue getValue(DBSession session) {
		if(value != null) {
			return new DataValue(value).setFormat(format);
		}else if(parameter != null) {
			return new DataValue(ParameterContext.getValue(parameter)).setFormat(format);
		}else if(action != null) {
			return action.execute(session, DataValue.NULL_DATA_VALUE);
		}else {
			return method.returnExecuteResult(session, DataValue.NULL_DATA_VALUE);
		}
	}

	
	/**
	 * 
	 * @author DougLei
	 */
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
	
	
	/**
	 * 
	 * @author DougLei
	 */
	private class DataActionResultPick{
		private boolean all;
		private String[] names;
		public DataActionResultPick(boolean all, String[] names) {
			this.all = all;
			this.names = names;
		}
		
		public DataValue pickValue(DataValue defaultDataValue) {
			if(all) {
				return new DataValue(ParameterContext.getValueMap(Scope.LOCAL, names), DataType.OBJECT);
			}else {
				if(names != null) {
					Parameter tmpParameter = new Parameter("tmpParameter", Scope.LOCAL, null);
					if(names.length == 1) {
						tmpParameter.updateName(names[0]);		
						ActualParameter p = ParameterContext.getParameter(tmpParameter);
						return new DataValue(p.getValue(null), p.getDataType());
					}else {
						Map<String, Object> valueMap = new HashMap<String, Object>(names.length);
						for (String name : names) {
							tmpParameter.updateName(name);
							valueMap.put(name, ParameterContext.getValue(tmpParameter));
						}
						return new DataValue(valueMap, DataType.OBJECT);
					}
				}
			}
			return defaultDataValue;
		}
	}
}
