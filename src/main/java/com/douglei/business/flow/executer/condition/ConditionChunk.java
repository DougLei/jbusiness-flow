package com.douglei.business.flow.executer.condition;

import java.util.LinkedList;

import com.douglei.business.flow.executer.LogicalOP;

/**
 * 
 * @author DougLei
 */
public class ConditionChunk {
	private LinkedList<ConditionChunk> chunkList;
	protected boolean inverse;
	protected LogicalOP nextOP;
	
	public ConditionChunk() {}
	public ConditionChunk(boolean inverse, LogicalOP nextOP) {
		this.chunkList = new LinkedList<ConditionChunk>();
		this.inverse = inverse;
		this.nextOP = nextOP;
	}

	public LogicalOP getNextOP() {
		return nextOP;
	}

	public ConditionResult validate() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void pushChunk(ConditionChunk chunk) {
		chunkList.add(chunk);
	}
	public void pushChunk(Condition condition) {
		if(!chunkList.isEmpty() && chunkList.getLast().getNextOP() == LogicalOP.AND) {
			chunkList.getLast().pushChunk(condition);
		}else {
			chunkList.add(condition);
		}
	}
	public void pushChunk(byte size, ConditionChunk[] chunks, LogicalOP cgcop) {
		chunkList.add(new ConditionChunks(size, chunks, cgcop));
	}
}
