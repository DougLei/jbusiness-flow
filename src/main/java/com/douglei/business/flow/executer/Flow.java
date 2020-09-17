package com.douglei.business.flow.executer;

import java.io.Serializable;

import com.douglei.business.flow.executer.action.ExecuteParameter;
import com.douglei.business.flow.executer.condition.ConditionValidator;

/**
 * 
 * @author DougLei
 */
public class Flow implements Serializable{
	private static final long serialVersionUID = -4546623542765944082L;
	private int type; // 0顺序流, 2条件流
	private int order;
	private String sourceEvent;
	private String targetEvent;
	private ConditionValidator conditionValidator;
	
	public Flow(int type, int order, String sourceEvent, String targetEvent, ConditionValidator conditionValidator) {
		this.type = type;
		this.order = order;
		this.sourceEvent = sourceEvent;
		this.targetEvent = targetEvent;
		this.conditionValidator = conditionValidator;
	}
	
	private Event targetEvent_;
	public void setTargetEvent(Event targetEvent) {
		this.targetEvent_ = targetEvent;
	}
	public Event targetEvent() { // 目标event实例
		return targetEvent_;
	}
	
	/**
	 * 执行, 对条件进行验证, 获取结果
	 * @param executeParameter
	 * @return
	 */
	public boolean execute(ExecuteParameter executeParameter) {
		return conditionValidator.validate(executeParameter);
	}
	
	// 是否是顺序流
	public boolean isSequence() {
		return type == 0;
	}
	
	public int getType() {
		return type;
	}
	public int getOrder() {
		return order;
	}
	public String getSourceEvent() {
		return sourceEvent;
	}
	public String getTargetEvent() {
		return targetEvent;
	}
}
