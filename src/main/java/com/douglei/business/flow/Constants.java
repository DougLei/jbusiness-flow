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
	
	public static final byte DATATYPE_STRING = 1; // 数据类型: string
	public static final byte DATATYPE_BYTE = 2; // 数据类型: byte
	public static final byte DATATYPE_SHORT = 3; // 数据类型: short
	public static final byte DATATYPE_INTEGER = 4; // 数据类型: int
	public static final byte DATATYPE_LONG = 5; // 数据类型: long
	public static final byte DATATYPE_FLOAT = 6; // 数据类型: float
	public static final byte DATATYPE_DOUBLE = 7; // 数据类型: double
	public static final byte DATATYPE_BOOLEAN = 8; // 数据类型: 布尔
	public static final byte DATATYPE_DATE = 9; // 数据类型: 日期
	public static final byte DATATYPE_ARRAY = 10; // 数据类型: 数组
	public static final byte DATATYPE_LIST = 11; // 数据类型: 集合
	public static final byte DATATYPE_OBJECT = 12; // 数据类型: 对象
	
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
