package com.douglei.business.flow.executer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.douglei.business.flow.Constants;
import com.douglei.business.flow.executer.action.Action;

/**
 * 
 * @author DougLei
 */
public class Event {
	private byte type;
	private String name;
	private String description;
	
	public Event(byte type, String name, String description) {
		this.type = type;
		this.name = name;
		this.description = description;
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
	
	private Action[] actions;
	public void setActions(Action[] actions) {
		this.actions = actions;
	}
	
	public boolean isStart() {
		return type == Constants.EVENT_START;
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
	
	public Object execute(Object param) {
		// TODO 
		System.out.println(actions);
		return null;
	}
}
