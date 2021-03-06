package com.douglei.business.flow.executer.action.impl.sql.op;

import java.util.List;

import com.douglei.business.flow.db.DBSession;
import com.douglei.business.flow.executer.parameter.Parameter;

/**
 * 
 * @author DougLei
 */
public class QueryLDVExecuter extends QueryExecuter {
	private static final long serialVersionUID = -6935642439781616148L;

	public QueryLDVExecuter(Parameter pageNum, Parameter pageSize) {
		super.type = ExecuterType.PAGE;
		setPageQueryParameters(pageNum, pageSize);
	}

	/**
	 * 执行查询
	 * @param session
	 * @param sql
	 * @param values
	 * @return
	 */
	@Override
	public Object executeQuery(DBSession session, String sql, List<Object> values) {
		return session.pageQuery(getPageNumVal(), getPageSizeVal(), sql, values);
	}
	
	private int pageNumVal;
	private int pageSizeVal;
	
	private int getPageNumVal() {
		if(pageNumVal == 0) {
			pageNumVal = getIntegerValue(pageNum);
			if(pageNumVal < 0) {
				pageNumVal = 1;
			}
		}else {
			pageNumVal++;
		}
		return pageNumVal;
	}
	private int getPageSizeVal() {
		if(pageSizeVal == 0) {
			pageSizeVal = getIntegerValue(pageSize);
			if(pageSizeVal < 0) {
				pageSizeVal = 1000;
			}
		}
		return pageSizeVal;
	}
}
