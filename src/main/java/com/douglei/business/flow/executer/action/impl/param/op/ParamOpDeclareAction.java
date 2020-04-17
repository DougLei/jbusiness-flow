package com.douglei.business.flow.executer.action.impl.param.op;

import com.douglei.business.flow.db.DBSession;
import com.douglei.business.flow.executer.ParameterContext;
import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.parameter.DeclaredParameter;

/**
 * 
 * @author DougLei
 */
public class ParamOpDeclareAction extends Action{
	private ParamDeclare[] declares;
	
	public ParamOpDeclareAction(byte size) {
		declares = new ParamDeclare[size];
	}
	
	public void addParam(byte index, DeclaredParameter parameter) {
		declares[index] = new ParamDeclare(parameter);
	}
	public void addRefParam(byte index, DeclaredParameter refParameter) {
		if(refParameter != null) {
			declares[index].setRefParameter(refParameter);
		}
	}

	@Override
	public Object execute(DBSession session) {
		for (ParamDeclare paramDeclare : declares) {
			ParameterContext.addParameter(paramDeclare.parameter, paramDeclare.getActualValue());
		}
		return null;
	}
	
	
	private class ParamDeclare{
		private DeclaredParameter parameter;
		private DeclaredParameter refParameter;
		
		ParamDeclare(DeclaredParameter parameter) {
			this.parameter = parameter;
		}
		void setRefParameter(DeclaredParameter refParameter) {
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
