package com.douglei.business.flow.executer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.action.ExecuteParameter;
import com.douglei.business.flow.executer.parameter.Scope;

/**
 * 
 * @author DougLei
 */
public class Event implements Serializable {
	private static final long serialVersionUID = 8465283886981706980L;
	private byte type; // 0一般事件, 1起始事件, 2结束事件
	private String name;
	private Action[] actions;
	
	public Event(byte type, String name, Action[] actions) {
		this.type = type;
		this.name = name;
		this.actions = actions;
	}
	
	private List<Flow> flows;
	public void addFlow(Flow flow) {
		if(flows == null) 
			flows = new ArrayList<Flow>(flow.isSequence()?1:4);
		flows.add(flow);
		if(flows.size() > 1) 
			flows.sort(flowComparator);
	}
	
	// 流排序的比较器
	private static Comparator<Flow> flowComparator = new Comparator<Flow>() {
		@Override
		public int compare(Flow f1, Flow f2) {
			if(f1.getOrder() == f2.getOrder()) {
				return 0;
			}else if(f1.getOrder() < f2.getOrder()) {
				return -1;
			}
			return 1;
		}
	};
	
	public boolean isStart() {
		return type == 1;
	}
	public byte getType() {
		return type;
	}
	public String getName() {
		return name;
	}
	
	/**
	 * 执行
	 * @param executeParameter
	 */
	public void execute(ExecuteParameter executeParameter) {
		for (Action action : actions) 
			action.execute(executeParameter);
		
		if(type != 2 && flows != null) {
			ParameterContext.clear(Scope.LOCAL); // 清空执行action时产生的本地参数
			toNextEvent(executeParameter);
		}
	}
	
	// 到下一个事件
	private void toNextEvent(ExecuteParameter executeParameter) {
		if(flows.size() == 1) { // 顺序流
			flows.get(0).targetEvent().execute(executeParameter);
		}else { // 条件流
			flows.forEach(flow -> {
				if(flow.execute(executeParameter)) {
					ParameterContext.clear(Scope.LOCAL); // 清空flow验证时产生的本地参数
					flow.targetEvent().execute(executeParameter);
					return;
				}
			});
		}
	}
}
