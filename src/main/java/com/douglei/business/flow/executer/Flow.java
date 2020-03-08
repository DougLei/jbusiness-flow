package com.douglei.business.flow.executer;

import com.douglei.business.flow.Constants;
import com.douglei.business.flow.executer.condition.ConditionGroup;

/**
 * 
 * @author DougLei
 */
public class Flow {
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
	
	public boolean isSequence() {
		return type == Constants.FLOW_SEQUENCE;
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
