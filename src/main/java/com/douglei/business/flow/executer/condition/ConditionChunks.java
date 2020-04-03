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
	
	public ConditionChunks(ConditionChunk[] chunks, LogicalOP cgcop) {
		for (ConditionChunk chunk : chunks) {
			if(chunk == null)
				break;
			this.size++;
		}
		this.chunks = chunks;
		this.nextOP = cgcop;
	}
	
	@Override
	public LogicalOP getNextOP() {
		if(list == null)
			return nextOP;
		return list.getLast().getNextOP();
	}
	
	@Override
	public void appendChunk(Condition condition) {
		if(list == null)
			list = new LinkedList<ConditionChunk>();
		list.add(condition);
	}

	@Override
	public ConditionResult validate() {
		ConditionResult result = chunks[0].validate();
		if(size > 1) {
			byte index = 1;
			do {
				result.merge(chunks[index]);
			}while(++index < size);
		}
		if(list != null) 
			list.forEach(l -> result.merge(l));
		return result;
	}
}
