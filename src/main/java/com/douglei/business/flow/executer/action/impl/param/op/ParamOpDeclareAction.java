package com.douglei.business.flow.executer.action.impl.param.op;

import com.douglei.business.flow.db.SessionWrapper;
import com.douglei.business.flow.executer.ParameterContext;
import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.parameter.Parameter;

/**
 * 
 * @author DougLei
 */
public class ParamOpDeclareAction extends Action{
	private ParamDeclare[] declares;
	
	public ParamOpDeclareAction(byte size) {
		declares = new ParamDeclare[size];
	}
	
	public void addParam(byte index, Parameter parameter) {
		declares[index] = new ParamDeclare(parameter);
	}
	public void addRefParam(byte index, Parameter refParameter) {
		if(refParameter != null) {
			declares[index].setRefParameter(refParameter);
		}
	}

	@Override
	public Object execute(SessionWrapper session) {
		for (ParamDeclare paramDeclare : declares) {
			ParameterContext.addParameter(paramDeclare.parameter, paramDeclare.getActualValue());
		}
		return null;
	}
	
	
	private class ParamDeclare{
		private Parameter parameter;
		private Parameter refParameter;
		
		ParamDeclare(Parameter parameter) {
			this.parameter = parameter;
		}
		void setRefParameter(Parameter refParameter) {
			this.refParameter = refParameter;
		}
		
		Object getActualValue() {
			Object actualValue;
			if(refParameter == null) {
				actualValue = parameter.getDefaultValue();
			}else {
				actualValue = ParameterContext.getValue(refParameter);
			}
			return actualValue;
		}
	}
}
