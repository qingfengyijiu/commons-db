package com.zhangjx.commons.db.pager;

import com.zhangjx.commons.db.enumeration.Order;

/**
 * Class for pager info.
 * @author zhang jianxin
 *
 */
public class Pager {
	
	private static final int PAGE_START = 1;
	
	private static final int DEFAULT_PAGE_SIZE = 10;
	
	private static final String DEFAULT_ORDER = Order.DESC.getName();
	
	private static final int DEFAULT_PAGE_TOTAL = 1;

	// current page number
	private int currentPage = PAGE_START;
	
	// the count of all the records
	private int recordsCount;
	
	// the records number in a page
	private int pageSize = DEFAULT_PAGE_SIZE;
	
	// sort column name
	private String sortColumn;
	
	// sort direction : DESC OR ASC
	private String order = DEFAULT_ORDER;
	
	// 总页数，默认为1
	private int totalPage = DEFAULT_PAGE_TOTAL;
	
	public Pager() {
		
	}
	
	public Pager(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getRecordsCount() {
		return recordsCount;
	}

	public void setRecordsCount(int recordsCount) {
		this.recordsCount = recordsCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
	
	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public boolean hasPrevPage() {
		return currentPage > PAGE_START;
	}
	
	public boolean hasNextPage() {
		return currentPage < totalPage;
	}
	
	/**
	 * 获取当前页所需的数据位移量
	 * @return
	 */
	public int offset() {
		return (currentPage - 1) * pageSize;
	}
	
}
