package com.douglei.business.flow.executer.action.impl.data.op.compare;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.action.ExecuteParameter;
import com.douglei.business.flow.executer.action.impl.data.op.Data;
import com.douglei.business.flow.executer.parameter.ResultParameter;

/**
 * 
 * @author DougLei
 */
public class DataOpCompareAction extends Action {
	private static final Logger logger = LoggerFactory.getLogger(DataOpCompareAction.class);
	private CompareType op;
	private Data left;
	private Data right;

	public DataOpCompareAction(CompareType op, Data left, Data right, ResultParameter result) {
		this.op = op;
		this.left = left;
		this.right = right;
		super.result = result;
	}

	@Override
	public Object execute(ExecuteParameter executeParameter) {
		if(logger.isDebugEnabled())
			logger.debug("执行[{}]", getClass().getName());
		return setResult(op.compare(left.getValue(executeParameter), right==null?null:right.getValue(executeParameter)));
	}
}
