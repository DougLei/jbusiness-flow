package com.douglei.business.flow.executer;

import java.util.Collections;
import java.util.Map;

import com.douglei.business.flow.db.Session;
import com.douglei.business.flow.executer.parameter.Parameter;
import com.douglei.business.flow.executer.parameter.Scope;

/**
 * 
 * @author DougLei
 */
public class BusinessFlow {
	private String name;
	private String description;
	private String version;
	
	private Parameter[] inputParameters;
	private Event startEvent;
	
	public BusinessFlow(String name, String description, String version) {
		this.name = name;
		this.description = description;
		this.version = version;
	}
	
	public Map<String, Object> execute() {
		return execute(Collections.emptyMap(), null);
	}
	
	public Map<String, Object> execute(Map<String, Object> inputValueMap) {
		return execute(inputValueMap, null);
	}
	
	public Map<String, Object> execute(Session session) {
		return execute(Collections.emptyMap(), session);
	}
	
	public Map<String, Object> execute(Map<String, Object> inputValueMap, Session session) {
		try {
			ParameterContext.initial();
			if(inputParameters.length > 0) {
				for (Parameter parameter : inputParameters) {
					ParameterContext.addParameter(parameter, inputValueMap.get(parameter.getName()));
				}
			}
			
			startEvent.execute(session);
			return ParameterContext.getValueMap(Scope.OUT);
		} catch(Exception e){
			throw new BusinessFlowExecuteException(name, description, e);
		}finally {
			ParameterContext.destory();
		}
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
	public void setInputParameters(Parameter[] inputParameters) {
		this.inputParameters = inputParameters;
	}
	public void setStartEvent(Event startEvent) {
		this.startEvent = startEvent;
	}
}
