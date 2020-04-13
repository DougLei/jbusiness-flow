package com.douglei.business.flow.executer.condition;

import com.douglei.business.flow.db.Session;

/**
 * 
 * @author DougLei
 */
public class ConditionValidator {
	private byte size;
	private ConditionChunk[] chunks;
	
	public ConditionValidator(ConditionChunk[] chunks) {
		for (ConditionChunk chunk : chunks) {
			if(chunk == null)
				break;
			this.size++;
		}
		this.chunks = chunks;
	}
	
	public boolean validate(Session session) {
		if(size == 0) 
			return true;
		ConditionResult result = chunks[0].validate(session);
		if(size == 1)
			return result.getBooleanValue();
		
		byte index = 1;
		do {
			result.merge(chunks[index], session);
		}while(++index < size);
		return result.getBooleanValue();
	}
	
	private ConditionValidator() {}
	private static final ConditionValidator DEFAULT_VALIDATOR = new ConditionValidator();
	/**
	 * 获取默认的验证器
	 * @return
	 */
	public static final ConditionValidator defaultValidator() {
		return DEFAULT_VALIDATOR;
	}
}
