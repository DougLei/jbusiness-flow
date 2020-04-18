package com.douglei.business.flow.executer.action.impl.data.op.arithmetic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.business.flow.db.DBSession;
import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.parameter.ResultParameter;
import com.douglei.tools.instances.ognl.OgnlHandler;


/**
 * 
 * @author DougLei
 */
public class DataOpArithmeticAction extends Action {
	private static final Logger logger = LoggerFactory.getLogger(DataOpArithmeticAction.class);
	private DataOpArithmetic[] group;

	public DataOpArithmeticAction(DataOpArithmetic[] group, ResultParameter result) {
		this.group = group;
		this.result = result;
	}
	
	@Override
	public Object execute(DBSession session) {
		if(logger.isDebugEnabled())
			logger.debug("执行[{}]", getClass().getName());
		StringBuilder formula = new StringBuilder(40);
		ArithmeticUtil.append(formula, group, session);
		logger.debug("获取的运算表达式为: {}", formula);
		return setResult(OgnlHandler.singleInstance().getObjectValue(formula.toString()));
	}
}
