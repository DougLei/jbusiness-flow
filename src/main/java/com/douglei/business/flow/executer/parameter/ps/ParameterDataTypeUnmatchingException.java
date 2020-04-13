package com.douglei.business.flow.executer.parameter.ps;

import com.douglei.business.flow.executer.parameter.Parameter;
import com.douglei.business.flow.executer.parameter.Scope;

/**
 * 参数的类型不匹配异常
 * @author DougLei
 */
public class ParameterDataTypeUnmatchingException extends RuntimeException {
	private static final long serialVersionUID = -4434086493685166256L;

	public ParameterDataTypeUnmatchingException(Scope scope, Parameter oldParameter, Parameter parameter) {
		super("在范围"+scope.getDescription()+"中, 已存在名为"+oldParameter.getName()+", 类型为"+oldParameter.getDataType().name()+"的参数实例, 无法将后来同名, 但类型为"+parameter.getDataType()+"的参数值插入, 可考虑对后来的参数进行重命名后插入");
	}
}
