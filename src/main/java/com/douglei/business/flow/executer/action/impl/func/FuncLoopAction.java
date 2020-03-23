package com.douglei.business.flow.executer.action.impl.func;

import java.util.Collection;

import com.douglei.business.flow.executer.ParameterContext;
import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.parameter.Parameter;

/**
 * 
 * @author DougLei
 */
public class FuncLoopAction extends Action {
	private Parameter collection;
	private Parameter alias;
	private Action[] actions;
	
	public FuncLoopAction(Parameter collection, Parameter alias, Action[] actions) {
		this.collection = collection;
		this.alias = alias;
		this.actions = actions;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Object execute() {
		Object value = ParameterContext.getValue(collection);
		if(value != null) {
			if(value instanceof Collection<?>) {
				executeList((Collection<Object>)value);
			}else if (value.getClass().isArray()){
				executeArray((Object[])value);
			}
		}
		return null;
	}
	
	// 集合
	private void executeList(Collection<Object> list) {
		if(list.size() > 0) {
			ParameterContext.addParameter(alias, null);
			for (Object object : list) {
				executeCore(object);
			}
		}
	}

	// 数组
	private void executeArray(Object[] array) {
		if(array.length > 0) {
			ParameterContext.addParameter(alias, null);
			for (Object object : array) {
				executeCore(object);
			}
		}
	}
	
	/**
	 * 执行的核心
	 * @param acutalAliasValue 别名参数的实际值
	 */
	private void executeCore(Object acutalAliasValue) {
		ParameterContext.updateValue(alias, acutalAliasValue);
		for (Action action : actions) {
			action.execute();
		}
	}
}
