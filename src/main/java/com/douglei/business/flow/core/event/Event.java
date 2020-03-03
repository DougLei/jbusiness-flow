package com.douglei.business.flow.core.event;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.douglei.business.flow.Constants;
import com.douglei.business.flow.core.flow.Flow;

/**
 * 
 * @author DougLei
 */
public class Event {
	/**
	 * {@link Constants}
	 */
	private byte type;
	private String name;
	private String description;
	
	// TODO 缺少actions属性
	private List<Flow> flows;
	
	
	public Event(byte type, String name, String description) {
		this.type = type;
		this.name = name;
		this.description = description;
	}

	public Object execute(Object param) {
		// TODO 
		return null;
	}
	
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
		return type == Constants.EVENT_START;
	}
	public boolean isEnd() {
		return type == Constants.EVENT_END;
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
}
