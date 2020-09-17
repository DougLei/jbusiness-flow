package com.douglei.business.flow.executer;

import com.douglei.business.flow.executer.parameter.DeclaredParameter;

/**
 * 
 * @author DougLei
 */
public class BusinessFlowBuilder {
	private BusinessFlow bf;
	
	public BusinessFlowBuilder(String name, String version) {
		this.bf = new BusinessFlow(name, version);
	}
	
	/**
	 * 设置输入参数
	 * @param inputParameters
	 */
	public void setInputParameters(DeclaredParameter[] inputParameters) {
		this.bf.setInputParameters(inputParameters);
	}
	
	/**
	 * 设置StartEvent
	 * @param startEvent
	 */
	public void setStartEvent(Event startEvent) {
		this.bf.setStartEvent(startEvent);
	}

	public BusinessFlow build() {
		return bf;
	}
}
