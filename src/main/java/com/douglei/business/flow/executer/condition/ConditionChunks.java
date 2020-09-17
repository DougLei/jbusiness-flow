package com.douglei.business.flow.executer.condition;

import java.util.LinkedList;

import com.douglei.business.flow.executer.LogicalOP;
import com.douglei.business.flow.executer.action.ExecuteParameter;

/**
 * 
 * @author DougLei
 */
class ConditionChunks extends ConditionChunk{
	private static final long serialVersionUID = 2132110066280080878L;
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
	public ConditionResult validate(ExecuteParameter executeParameter) {
		ConditionResult result = chunks[0].validate(executeParameter);
		if(size > 1) {
			byte index = 1;
			do {
				result.merge(chunks[index], executeParameter);
			}while(++index < size);
		}
		if(list != null) 
			list.forEach(l -> result.merge(l, executeParameter));
		return result;
	}
}
