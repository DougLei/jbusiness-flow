package com.douglei.business.flow.executer;

import com.douglei.business.flow.executer.condition.ConditionGroup;
import com.douglei.business.flow.executer.condition.ConditionUtil;

/**
 * 
 * @author DougLei
 */
public class Flow {
	public static final byte FLOW_TYPE_SEQUENCE = 0; // 流类型: 顺序流
	public static final byte FLOW_TYPE_CONDITION = 1; // 流类型: 条件流
	
	private String description;
	private byte type;
	private byte order;
	private String sourceEvent;
	private String targetEvent;
	private ConditionGroup[] conditionGroups;
	
	public Flow(String description, byte type, byte order, String sourceEvent, String targetEvent, ConditionGroup[] conditionGroups) {
		this.description = description;
		this.type = type;
		this.order = order;
		this.sourceEvent = sourceEvent;
		this.targetEvent = targetEvent;
		this.conditionGroups = conditionGroups;
	}
	
	private Event event;
	public void linkEvent(Event event) {
		this.event = event;
	}
	public Event targetEvent() { // 目标event实例
		return event;
	}
	
	// 对条件进行验证, 获取结果
	public boolean validate() {
		if(conditionGroups.length == 0) {
			return true;
		}
		return ConditionUtil.validate(conditionGroups[0].validate(), conditionGroups[0].getOp(), 1, conditionGroups);
	}
	
	// 是否是顺序流
	public boolean isSequence() {
		return type == FLOW_TYPE_SEQUENCE;
	}
	
	public String getDescription() {
		return description;
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
