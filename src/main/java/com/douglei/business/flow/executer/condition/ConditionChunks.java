package com.douglei.business.flow.executer.condition;

import java.util.LinkedList;

import com.douglei.business.flow.executer.LogicalOP;

/**
 * 
 * @author DougLei
 */
class ConditionChunks extends ConditionChunk{
	private byte size;
	private ConditionChunk[] chunks;
	private LogicalOP cgcop;
	
	public ConditionChunks(byte size, ConditionChunk[] chunks, LogicalOP cgcop) {
		this.size = size;
		this.chunks = chunks;
		this.cgcop = cgcop;
	}
	
	@Override
	public LogicalOP getNextOP() {
		if(chunkList == null)
			return cgcop;
		return chunkList.getLast().getNextOP();
	}
	
	@Override
	public void pushChunk(Condition condition) {
		if(chunkList == null)
			chunkList = new LinkedList<ConditionChunk>();
		chunkList.add(condition);
	}
}
