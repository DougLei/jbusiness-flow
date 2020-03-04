package com.douglei.business.flow.core;

import java.util.List;

/**
 * 
 * @author DougLei
 */
public class BusinessFlow {
	private String name;
	private String description;
	private String version;
	private boolean enabled; 
	
	private List<Parameter> inputParameters;
	private Event startEvent;
	
	public BusinessFlow(String name, String description, String version, boolean enabled) {
		if(enabled) {
			this.name = name;
			this.description = description;
			this.version = version;
			this.enabled = true;
		}
	}
	
	
	
	public Object execute(Object param) {
		// TODO 验证输入参数, 然后传入输入参数
		System.out.println(inputParameters);
		return startEvent.execute(param);
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
	public boolean isEnabled() {
		return enabled;
	}
	public void setInputParameters(List<Parameter> inputParameters) {
		this.inputParameters = inputParameters;
	}
	public void setStartEvent(Event startEvent) {
		this.startEvent = startEvent;
	}
}
