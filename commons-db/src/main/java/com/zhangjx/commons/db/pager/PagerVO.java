package com.zhangjx.commons.db.pager;

import java.util.List;

/**
 * the wrapper for pager query result
 * @author zhang jianxin
 *
 * @param <T>
 */
public class PagerVO<T> {

	private List<T> list;
	
	private Pager pager;

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}
	
}
