package com.douglei.business.flow.executer.condition;

import com.douglei.business.flow.executer.LogicalOP;

/**
 * 
 * @author DougLei
 */
class ConditionChunks extends ConditionChunk{
	private byte size;
	private ConditionChunk[] chunks;
	
	public ConditionChunks(byte size, ConditionChunk[] chunks, LogicalOP nextOP) {
		this.size = size;
		this.chunks = chunks;
		this.nextOP = nextOP;
	}

	@Override
	public void pushChunk(Condition condition) {
		
	}
}
