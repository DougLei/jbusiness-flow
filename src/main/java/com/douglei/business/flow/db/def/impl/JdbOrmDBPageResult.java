package com.douglei.business.flow.db.def.impl;

import java.util.List;
import java.util.Map;

import com.douglei.business.flow.db.DBPageResult;
import com.douglei.orm.core.sql.pagequery.PageResult;

/**
 * 
 * @author DougLei
 */
public class JdbOrmDBPageResult implements DBPageResult {
	private PageResult<Map<String, Object>> pageResult;
	
	public JdbOrmDBPageResult(PageResult<Map<String, Object>> pageResult) {
		this.pageResult = pageResult;
	}

	@Override
	public long getCount() {
		return pageResult.getCount();
	}

	@Override
	public int getPageNum() {
		return pageResult.getPageNum();
	}

	@Override
	public int getPageSize() {
		return pageResult.getPageSize();
	}

	@Override
	public int getPageCount() {
		return pageResult.getPageCount();
	}

	@Override
	public List<Map<String, Object>> getResultDatas() {
		return pageResult.getResultDatas();
	}

	@Override
	public boolean isFirstPage() {
		return pageResult.isFirstPage();
	}

	@Override
	public boolean isLastPage() {
		return pageResult.isLastPage();
	}

	@Override
	public boolean hasNextPage() {
		return pageResult.hasNextPage();
	}
}
