package com.douglei.business.flow;

/**
 * 
 * @author DougLei
 */
public class Constants {
	public static final byte PARAM_SCOPE_IN = 1; // 参数范围: 输入
	public static final byte PARAM_SCOPE_INOUT = 2; // 参数范围: 输入输出
	public static final byte PARAM_SCOPE_OUT = 3; // 参数范围: 输出
	public static final byte PARAM_SCOPE_GLOBAL = 4; // 参数范围: 全局
	public static final byte PARAM_SCOPE_LOCAL = 5; // 参数范围: 本地
	
	public static final byte EVENT_NORMAL = 0; // 事件类型: 一般
	public static final byte EVENT_START = 1; // 事件类型: 起始
	public static final byte EVENT_END = 2; // 事件类型: 结束
	
	public static final byte FLOW_SEQUENCE = 0; // 流类型: 顺序流
	public static final byte FLOW_CONDITION = 1; // 流类型: 条件流
	
	public static final byte SQL_INSERT = 1; // sql类型: insert
	public static final byte SQL_DELETE = 2; // sql类型: delete
	public static final byte SQL_UPDATE = 3; // sql类型: update
	public static final byte SQL_SELECT = 4; // sql类型: select
}
