package com.douglei.business.flow.executer.condition;

import java.util.Iterator;
import java.util.LinkedList;

import com.douglei.business.flow.db.Session;
import com.douglei.business.flow.executer.LogicalOP;

/**
 * 
 * @author DougLei
 */
public class ConditionChunk {
	protected LinkedList<ConditionChunk> list;
	protected boolean inverse;
	protected LogicalOP nextOP;
	
	public ConditionChunk() {}
	public ConditionChunk(boolean inverse, LogicalOP nextOP) {
		this.list = new LinkedList<ConditionChunk>();
		this.inverse = inverse;
		this.nextOP = nextOP;
	}

	public LogicalOP getNextOP() {
		if(list.getLast().getClass() == ConditionChunk.class)
			return list.getLast().getNextOP();
		return nextOP;
	}

	public final void appendChunk(ConditionChunk chunk) {
		list.add(chunk);
	}
	public final void appendChunk(ConditionChunk[] chunks, LogicalOP cgcop) {
		list.add(new ConditionChunks(chunks, cgcop));
	}
	public void appendChunk(Condition condition) {
		if(!list.isEmpty() && list.getLast().getNextOP() == LogicalOP.AND) {
			list.getLast().appendChunk(condition);
		}else {
			list.add(condition);
		}
	}
	
	public ConditionResult validate(Session session) {
		Iterator<ConditionChunk> iterator = list.iterator();
		
		ConditionResult result = iterator.next().validate(session);
		ConditionChunk chunk = null;
		while(iterator.hasNext()) {
			chunk = iterator.next();
			if(chunk.getClass() == ConditionChunk.class)
				break;
			result.merge(chunk, session);
		}
		result.update(inverse, nextOP);
		
		if(chunk != null && chunk.getClass() == ConditionChunk.class) {
			while(true) {
				result.merge(chunk, session);
				if(iterator.hasNext()) {
					chunk = iterator.next();
				}else {
					break;
				}
			}
		}
		return result;
	}
}