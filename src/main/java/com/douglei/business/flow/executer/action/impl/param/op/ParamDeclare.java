package com.douglei.business.flow.executer.action.impl.param.op;

import com.douglei.business.flow.executer.ParameterContext;
import com.douglei.business.flow.executer.parameter.DeclaredParameter;
import com.douglei.business.flow.executer.parameter.Parameter;

/**
 * 
 * @author DougLei
 */
class ParamDeclare {
	private DeclaredParameter declaredParameter;
	private Parameter refParameter;
	
	ParamDeclare(DeclaredParameter declaredParameter) {
		this.declaredParameter = declaredParameter;
	}
	void setRefParameter(Parameter refParameter) {
		this.refParameter = refParameter;
	}
	
	Object getActualValue() {
		Object actualValue;
		if(refParameter == null) {
			actualValue = declaredParameter.getDefaultValue();
		}else {
			actualValue = ParameterContext.getValue(refParameter);
		}
		return actualValue;
	}
	
	public DeclaredParameter getDeclareParameter() {
		return declaredParameter;
	}
}
