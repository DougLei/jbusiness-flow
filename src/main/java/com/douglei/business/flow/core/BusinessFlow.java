package com.douglei.business.flow.core;

import java.io.Serializable;

import com.douglei.business.flow.core.event.StartEvent;

/**
 * 
 * @author DougLei
 */
public class BusinessFlow implements Serializable{
	private String name;
	private String description;
	private String version;
	private byte state; 
	private StartEvent event;
	
	public BusinessFlow(String name, String description, String version, byte state) {
		this.name = name;
		this.description = description;
		this.version = version;
		this.state = state;
	}
	
	public Object execute(Object param) {
		return event.start(param);
	}
	
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public String getVersion() {
		return version;
	}
	public byte getState() {
		return state;
	}
	public void setEvent(StartEvent event) {
		this.event = event;
	}
	
	@Override
	public String toString() {
		return "BusinessFlow [name=" + name + ", description=" + description + ", version=" + version + ", state=" + state + "]";
	}
}
