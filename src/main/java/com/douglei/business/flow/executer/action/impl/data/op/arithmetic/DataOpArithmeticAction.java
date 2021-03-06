package com.douglei.business.flow.executer.action.impl.data.op.arithmetic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.action.ExecuteParameter;
import com.douglei.business.flow.executer.parameter.ResultParameter;
import com.douglei.tools.OgnlUtil;


/**
 * 
 * @author DougLei
 */
public class DataOpArithmeticAction extends Action {
	private static final long serialVersionUID = -4103704066921507283L;
	private static final Logger logger = LoggerFactory.getLogger(DataOpArithmeticAction.class);
	private DataOpArithmetic[] group;

	public DataOpArithmeticAction(DataOpArithmetic[] group, ResultParameter result) {
		this.group = group;
		this.result = result;
	}
	
	@Override
	public Object execute(ExecuteParameter executeParameter) {
		if(logger.isDebugEnabled())
			logger.debug("执行[{}]", getClass().getName());
		StringBuilder formula = new StringBuilder(40);
		ArithmeticUtil.append(formula, group, executeParameter);
		logger.debug("获取的运算表达式为: {}", formula);
		return setResult(OgnlUtil.getObjectValue(formula.toString()));
	}
}
