package com.douglei.business.flow.executer.action.impl.param.op;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.business.flow.db.DBSession;
import com.douglei.business.flow.executer.ParameterContext;
import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.parameter.DeclaredParameter;
import com.douglei.business.flow.executer.parameter.Parameter;

/**
 * 
 * @author DougLei
 */
public class ParamOpDeclareAction extends Action{
	private static final Logger logger = LoggerFactory.getLogger(ParamOpDeclareAction.class);
	private ParamDeclare[] declares;
	
	public ParamOpDeclareAction(byte size) {
		declares = new ParamDeclare[size];
	}
	
	public void addDeclareParameter(byte index, DeclaredParameter declaredParameter) {
		declares[index] = new ParamDeclare(declaredParameter);
	}
	public void addRefParameter(byte index, Parameter refParameter) {
		declares[index].setRefParameter(refParameter);
	}

	@Override
	public Object execute(DBSession session) {
		if(logger.isDebugEnabled())
			logger.debug("执行[{}]", getClass().getName());
		for (ParamDeclare paramDeclare : declares)
			ParameterContext.addParameter(paramDeclare.getDeclareParameter(), paramDeclare.getActualValue());
		return null;
	}
}
