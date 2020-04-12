package com.douglei.business.flow.executer;

import java.util.Map;

import com.douglei.business.flow.executer.parameter.Parameter;
import com.douglei.business.flow.executer.parameter.Scope;
import com.douglei.business.flow.session.SessionPool;

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
	
	private SessionPool pool;
	
	public BusinessFlow(String name, String description, String version, SessionPool pool) {
		this.name = name;
		this.description = description;
		this.version = version;
		this.pool = pool;
	}
	
	public Map<String, Object> execute(Map<String, Object> inputValueMap) {
		try {
			ParameterContext.initial(pool);
			if(inputParameters.length > 0) {
				for (Parameter parameter : inputParameters) {
					ParameterContext.addParameter(parameter, inputValueMap.get(parameter.getName()));
				}
			}
			startEvent.execute();
			return ParameterContext.getValueMap(Scope.OUT);
		} catch(Exception ex){
			
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
