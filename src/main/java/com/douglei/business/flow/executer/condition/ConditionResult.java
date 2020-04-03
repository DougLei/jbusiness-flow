package com.douglei.business.flow.executer.condition;

import com.douglei.business.flow.executer.LogicalOP;

/**
 * 
 * @author DougLei
 */
class ConditionResult {
	private boolean booleanValue;
	private LogicalOP nextOP;
	
	public ConditionResult(boolean booleanValue, LogicalOP nextOP) {
		this.booleanValue = booleanValue;
		this.nextOP = nextOP;
	}

	// 是否短路
	private boolean isShortCircuit() {
		return (booleanValue && nextOP == LogicalOP.OR) 
				|| (!booleanValue && nextOP == LogicalOP.AND);
	}
	
	public void merge(ConditionChunk chunk) {
		if(!isShortCircuit()) {
			booleanValue = nextOP.operating(booleanValue, chunk.validate().booleanValue);
		}
		this.nextOP = chunk.nextOP;
	}
	
	public boolean getBooleanValue() {
		return booleanValue;
	}
	
	// 目前只在ConditionChunk中使用
	public void update(boolean inverse, LogicalOP nextOP) {
		if(inverse)
			booleanValue = !booleanValue;
		this.nextOP = nextOP;
	}
}
