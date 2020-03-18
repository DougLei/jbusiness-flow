package com.douglei.business.flow.executer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.douglei.business.flow.executer.action.Action;

/**
 * 
 * @author DougLei
 */
public class Event {
	public static final byte EVENT_TYPE_NORMAL = 0; // 事件类型: 一般
	public static final byte EVENT_TYPE_START = 1; // 事件类型: 起始
	public static final byte EVENT_TYPE_END = 2; // 事件类型: 结束
	
	private byte type;
	private String name;
	private String description;
	private Action[] actions;
	
	public Event(byte type, String name, String description, Action[] actions) {
		this.type = type;
		this.name = name;
		this.description = description;
		this.actions = actions;
	}
	
	private List<Flow> flows;
	public void linkFlows(Flow flow) {
		if(flows == null) {
			flows = new ArrayList<Flow>(flow.isSequence()?1:4);
		}
		flows.add(flow);
		if(flows.size() > 1) {
			flows.sort(new Comparator<Flow>() {
				@Override
				public int compare(Flow f1, Flow f2) {
					if(f1.getOrder() == f2.getOrder()) {
						return 0;
					}else if(f1.getOrder() < f2.getOrder()) {
						return -1;
					}
					return 1;
				}
			});
		}
	}
	
	public boolean isStart() {
		return type == EVENT_TYPE_START;
	}
	public byte getType() {
		return type;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	
	public void execute() {
		for (Action action : actions) {
			action.execute();
		}
		if(type == EVENT_TYPE_NORMAL)
			toNextEvent();
	}
	
	// 到下一个事件
	private void toNextEvent() {
		flows.forEach(flow -> {
			if(flow.validate()) {
				flow.targetEvent().execute();
				return;
			}
		});
	}
}
