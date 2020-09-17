package com.douglei.business.flow.executer;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

import com.douglei.business.flow.db.DBSession;
import com.douglei.business.flow.executer.action.ExecuteParameter;
import com.douglei.business.flow.executer.parameter.DeclaredParameter;
import com.douglei.business.flow.executer.parameter.Scope;

/**
 * 
 * @author DougLei
 */
public class BusinessFlow implements Serializable {
	private static final long serialVersionUID = 4076271443860212565L;
	private String name;
	private String version;
	
	private DeclaredParameter[] inputParameters; // 输入参数
	private Event startEvent; // StartEvent
	
	BusinessFlow(String name, String version) {
		this.name = name;
		this.version = version;
	}
	
	/**
	 * 执行业务流, 没有输入值, 不和数据库交互
	 * @return
	 */
	public Map<String, Object> execute() {
		return execute(Collections.emptyMap(), null);		
	}
	
	/**
	 * 执行业务流, 不和数据库交互
	 * @param inputValueMap 输入值map
	 * @return
	 */
	public Map<String, Object> execute(Map<String, Object> inputValueMap) {
		return execute(inputValueMap, null);		
	}
	
	/**
	 * 执行业务流, 没有输入值
	 * @param session 与数据库交互的session
	 * @return
	 */
	public Map<String, Object> execute(DBSession session) {
		return execute(Collections.emptyMap(), session);		
	}
	
	/**
	 * 执行业务流
	 * @param inputValueMap 输入值map
	 * @param session 与数据库交互的session
	 * @return
	 */
	public Map<String, Object> execute(Map<String, Object> inputValueMap, DBSession session) {
		boolean beginTransaction = session==null?false:session.beginTransaction();
		try {
			ParameterContext.initial();
			if(inputParameters != null) {
				for (DeclaredParameter parameter : inputParameters) {
					ParameterContext.addParameter(parameter, inputValueMap.get(parameter.getName()));
				}
			}
			
			startEvent.execute(new ExecuteParameter(session));
			Map<String, Object> vm = ParameterContext.getValueMap(Scope.OUT);
			if(beginTransaction)
				session.commit();
			return vm;
		} catch(Exception e){
			if(beginTransaction)
				session.rollback();
			throw new BusinessFlowExecuteException(name, e);
		}finally {
			session.close();
			ParameterContext.destory();
		}
	}
	
	public String getName() {
		return name;
	}
	public String getVersion() {
		return version;
	}
	void setInputParameters(DeclaredParameter[] inputParameters) {
		this.inputParameters = inputParameters;
	}
	void setStartEvent(Event startEvent) {
		this.startEvent = startEvent;
	}
}
