package com.douglei.business.flow.executer;

import com.douglei.business.flow.Constants;

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
	
	// TODO 缺少conditionGroups属性
	private Event nextEvent;
	
	public void linkNextEvent(Event nextEvent) {
		this.nextEvent = nextEvent;
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
