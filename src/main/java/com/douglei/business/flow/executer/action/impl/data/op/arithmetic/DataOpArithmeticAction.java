package com.douglei.business.flow.executer.action.impl.data.op.arithmetic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.business.flow.db.SessionWrapper;
import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.parameter.Parameter;
import com.douglei.tools.instances.ognl.OgnlHandler;


/**
 * 
 * @author DougLei
 */
public class DataOpArithmeticAction extends Action {
	private static final Logger logger = LoggerFactory.getLogger(DataOpArithmeticAction.class);
	private DataOpArithmetic[] group;

	public DataOpArithmeticAction(DataOpArithmetic[] group, Parameter result) {
		this.group = group;
		this.result = result;
	}
	
	@Override
	public Object execute(SessionWrapper session) {
		StringBuilder formula = new StringBuilder(40);
		ArithmeticUtil.append(formula, group, session);
		logger.debug("获取的运算表达式为: {}", formula);
		return setResult(OgnlHandler.singleInstance().getObjectValue(formula.toString()));
	}
}
