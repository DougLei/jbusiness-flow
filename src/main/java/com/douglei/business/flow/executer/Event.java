package com.douglei.business.flow.executer;

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
public class Event {
	// private static final byte EVENT_TYPE_NORMAL = 0; // 事件类型: 一般, 该属性值没有用上, 所以注释掉, 但是0代表的就是一般事件类型
	private static final byte EVENT_TYPE_START = 1; // 事件类型: 起始
	private static final byte EVENT_TYPE_END = 2; // 事件类型: 结束
	
	private byte type;
	private String name;
	private Action[] actions;
	
	public Event(byte type, String name, Action[] actions) {
		this.type = type;
		this.name = name;
		this.actions = actions;
	}
	
	private List<Flow> flows;
	public void linkFlows(Flow flow) {
		if(flows == null) {
			flows = new ArrayList<Flow>(flow.isSequence()?1:4);
		}
		flows.add(flow);
		if(flows.size() > 1) 
			flows.sort(flowComparator);
	}
	
	// 线排序的比较器
	private Comparator<Flow> flowComparator = new Comparator<Flow>() {
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
		return type == EVENT_TYPE_START;
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
		for (Action action : actions) {
			action.execute(executeParameter);
		}
		if(flows != null && type != EVENT_TYPE_END) {
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
