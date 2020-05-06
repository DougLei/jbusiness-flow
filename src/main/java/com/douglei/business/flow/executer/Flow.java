package com.douglei.business.flow.executer;

import com.douglei.business.flow.executer.action.ExecuteParameter;
import com.douglei.business.flow.executer.condition.ConditionValidator;

/**
 * 
 * @author DougLei
 */
public class Flow {
	private static final byte FLOW_TYPE_SEQUENCE = 0; // 流类型: 顺序流
	// private static final byte FLOW_TYPE_CONDITION = 1; // 流类型: 条件流, 该属性值没有用上, 所以注释掉, 但是1代表的就是条件流类型
	
	private byte type;
	private byte order;
	private String sourceEvent;
	private String targetEvent;
	private ConditionValidator conditionValidator;
	
	public Flow(byte type, byte order, String sourceEvent, String targetEvent, ConditionValidator conditionValidator) {
		this.type = type;
		this.order = order;
		this.sourceEvent = sourceEvent;
		this.targetEvent = targetEvent;
		this.conditionValidator = conditionValidator;
	}
	
	private Event event;
	public void linkEvent(Event event) {
		this.event = event;
	}
	public Event targetEvent() { // 目标event实例
		return event;
	}
	
	/**
	 * 执行, 对条件进行验证, 获取结果
	 * @param executeParameter
	 * @return
	 */
	public boolean execute(ExecuteParameter executeParameter) {
		return conditionValidator.validate(executeParameter);
	}
	
	// 是否是顺序流
	public boolean isSequence() {
		return type == FLOW_TYPE_SEQUENCE;
	}
	
	public byte getType() {
		return type;
	}
	public byte getOrder() {
		return order;
	}
	public String getSourceEvent() {
		return sourceEvent;
	}
	public String getTargetEvent() {
		return targetEvent;
	}
}
