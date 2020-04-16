package com.douglei.business.flow.executer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.douglei.business.flow.db.DBSession;
import com.douglei.business.flow.executer.action.Action;
import com.douglei.business.flow.executer.action.impl.sql.SqlAction;
import com.douglei.business.flow.executer.parameter.Scope;

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
	public boolean includeSqlAction() {
		for (Action action : actions) {
			if(action instanceof SqlAction) {
				return true;
			}
		}
		return false;
	}
	
	public void execute(DBSession session) {
		for (Action action : actions) {
			action.execute(session);
		}
		if(flows != null && type != EVENT_TYPE_END) {
			ParameterContext.clear(Scope.LOCAL); // 清空执行action时产生的本地参数
			toNextEvent(session);
		}
	}
	
	// 到下一个事件
	private void toNextEvent(DBSession session) {
		if(flows.size() == 1) { // 顺序流
			flows.get(0).targetEvent().execute(session);
		}else { // 条件流
			flows.forEach(flow -> {
				if(flow.validate(session)) {
					ParameterContext.clear(Scope.LOCAL); // 清空flow验证时产生的本地参数
					flow.targetEvent().execute(session);
					return;
				}
			});
		}
	}
}
