package com.example.demo.utils.common.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页查询时的辅助工具类
 *
 */
@SuppressWarnings("rawtypes")
public class Page<T> extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected int limit = 20; //每页显示条数
	protected int start = 0;  //起始行号
	protected long total = -1; //总数
	protected List<T> result = new ArrayList<T>(); //结果集
	protected int no = 1;   //第几页
	protected int pages = 0; //总页面码, jqGrid要求给出总页数，超级无聊

	public Page(){}
	public Page(int pageIndex,int pageSize){
		this.no = pageIndex;
		this.limit = pageSize;
	}

	//-- 访问查询结果函数 --//

	/**
	 * 获得页内的记录列表.
	 */
	public List getResult() {
		return result;
	}

	/**
	 * 设置页内的记录列表.
	 */
	public void setResult(final List<T> result) {
		this.result = result;
	}
	
	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getStart() {
//		return start;
		return (no - 1) * limit;
	}

//	public void setStart(int start) {
//		this.start = start;
//	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
//		this.start = (no - 1) * limit;
	}

	public int getPages() {
		return (int)((total / limit) + (total % limit > 0 ? 1 : 0));
	}
}
