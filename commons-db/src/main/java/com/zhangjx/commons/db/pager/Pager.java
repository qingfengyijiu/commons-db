package com.zhangjx.commons.db.pager;

/**
 * 分页信息类
 * @author zhang jianxin
 *
 */
public class Pager {
	
	private static final int PAGE_START = 1;
	
	private static final int DEFAULT_PAGE_SIZE = 10;
	
	private static final String DEFAULT_ORDER = "DESC";
	
	private static final int DEFAULT_PAGE_TOTAL = 1;

	// 当前页码从1开始
	private int currentPage = PAGE_START;
	
	//总记录数
	private int recordsCount;
	
	// 每页记录数
	private int pageSize = DEFAULT_PAGE_SIZE;
	
	// 排序字段
	private String sortColumn;
	
	// 排序方式，默认DESC
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
