package com.zhangjx.commons.db.pager;

/**
 * ��ҳ��Ϣ��
 * @author zhang jianxin
 *
 */
public class Pager {
	
	private static final int PAGE_START = 1;
	
	private static final int DEFAULT_PAGE_SIZE = 10;
	
	private static final String DEFAULT_ORDER = "DESC";
	
	private static final int DEFAULT_PAGE_TOTAL = 1;

	// ��ǰҳ���1��ʼ
	private int currentPage = PAGE_START;
	
	//�ܼ�¼��
	private int recordsCount;
	
	// ÿҳ��¼��
	private int pageSize = DEFAULT_PAGE_SIZE;
	
	// �����ֶ�
	private String sortColumn;
	
	// ����ʽ��Ĭ��DESC
	private String order = DEFAULT_ORDER;
	
	// ��ҳ����Ĭ��Ϊ1
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
	 * ��ȡ��ǰҳ���������λ����
	 * @return
	 */
	public int offset() {
		return (currentPage - 1) * pageSize;
	}
	
}
