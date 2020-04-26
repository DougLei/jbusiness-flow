package com.douglei.business.flow.db;

import java.util.List;
import java.util.Map;

/**
 * 数据库分页查询结果对象
 * @author DougLei
 */
public interface DBPageResult {
	
	/**
	 * 获取数据总数量
	 * @return
	 */
	public long getCount();
	
	/**
	 * 获取当前的页数, 即第几页
	 * @return
	 */
	public int getPageNum();
	
	/**
	 * 获取一页显示的数量
	 * @return
	 */
	public int getPageSize();
	
	/**
	 * 获取总页数
	 * @return
	 */
	public int getPageCount();
	
	/**
	 * 获取数据集
	 * @return
	 */
	public List<Map<String, Object>> getResultDatas();
	
	/**
	 * 是否是第一页
	 * @return
	 */
	public boolean isFirstPage();
	
	/**
	 * 是否是最后一页
	 * @return
	 */
	public boolean isLastPage();
}
