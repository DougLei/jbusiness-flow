package com.douglei.business.flow.executer;

import java.util.Map;

/**
 * 
 * @author DougLei
 */
public class BusinessFlow {
	private String name;
	private String description;
	private String version;
	private boolean enabled; 
	
	private Parameter[] inputParameters;
	private Event startEvent;
	
	public BusinessFlow(String name, String description, String version, boolean enabled) {
		if(enabled) {
			this.name = name;
			this.description = description;
			this.version = version;
			this.enabled = true;
		}
	}
	
//	public void execute(Object[] inputValues) {
//		// TODO 按照定义的顺序传入输入参数数组
//	}
	
	public void execute(Map<String, Object> inputValueMap) {
		// TODO 在实际传入的输入参数中找到配置的输入参数和输入输出参数, 并将其存储到ParameterContext的输入参数Map中
		if(inputParameters.length > 0) {
			ParameterContext.initialInputParameterMap((byte)inputParameters.length);
			for (Parameter parameter : inputParameters) {
				ParameterContext.addInputParameter(getInputParameter(parameter, inputValueMap.get(parameter.getName())));
			}
		}
		startEvent.execute();
	}
	
	/**
	 * 根据配置的输入参数, 以及实际传入的值, 获取相应的输入参数
	 * @param configInputParameter
	 * @param actualValue
	 * @return
	 */
	private Parameter getInputParameter(Parameter configInputParameter, Object actualValue) {
		if(actualValue == null && configInputParameter.isRequired() && configInputParameter.getDefaultValue() == null) {
			throw new NullPointerException("输入参数["+configInputParameter.getName()+"]的值不能为空");
		}
		if(actualValue != null && !configInputParameter.getDataType().matching(actualValue)) {
			throw new ClassCastException("输入参数["+configInputParameter.getName()+"]的值应为"+configInputParameter.getDataType().name()+"类型");
		}
		return Parameter.newInstance(configInputParameter, actualValue);
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
	public void setInputParameters(Parameter[] inputParameters) {
		this.inputParameters = inputParameters;
	}
	public void setStartEvent(Event startEvent) {
		this.startEvent = startEvent;
	}
}
