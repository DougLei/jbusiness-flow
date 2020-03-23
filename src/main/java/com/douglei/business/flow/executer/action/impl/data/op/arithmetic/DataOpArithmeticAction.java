package com.douglei.business.flow.executer.action.impl.data.op.arithmetic;

import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.parameter.Parameter;
import com.douglei.tools.instances.expression.resolver.ExpressionResolver;

/**
 * 
 * @author DougLei
 */
public class DataOpArithmeticAction extends Action {
	private DataOpArithmetic[] group;

	public DataOpArithmeticAction(DataOpArithmetic[] group, Parameter result) {
		this.group = group;
		this.result = result;
	}

	@Override
	public Object execute() {
		StringBuilder formula = new StringBuilder(40);
		ArithmeticUtil.append(formula, group);
		return setResult(new ExpressionResolver(formula.toString()).resolve());
	}
}
