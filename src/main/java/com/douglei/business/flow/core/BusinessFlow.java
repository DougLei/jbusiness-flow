package com.douglei.business.flow.core;

import java.io.Serializable;
import java.util.List;

import com.douglei.business.flow.core.event.StartEvent;
import com.douglei.business.flow.core.param.Parameter;

/**
 * 
 * @author DougLei
 */
public class BusinessFlow implements Serializable{
	private String name;
	private String description;
	private String version;
	private boolean enabled; 
	
	private List<Parameter> inputParameters;
	private StartEvent event;
	
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
	public boolean isEnabled() {
		return enabled;
	}
	public void setInputParameters(List<Parameter> inputParameters) {
		this.inputParameters = inputParameters;
	}
	public void setEvent(StartEvent event) {
		this.event = event;
	}
}
