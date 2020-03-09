package com.douglei.business.flow.executer.action.impl.param.op;

import com.douglei.business.flow.executer.Parameter;
import com.douglei.business.flow.executer.action.Action;

/**
 * 
 * @author DougLei
 */
public class ParamOpDeclareAction extends Action{
	private ParamDeclare[] declares;
	
	public ParamOpDeclareAction(int size) {
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
	public Object execute() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	private class ParamDeclare{
		private Parameter parameter;
		private Parameter refParameter;
		
		public ParamDeclare(Parameter parameter) {
			this.parameter = parameter;
		}
		public void setRefParameter(Parameter refParameter) {
			this.refParameter = refParameter;
		}
	}
}
