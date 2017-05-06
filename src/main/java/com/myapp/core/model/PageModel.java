package com.myapp.core.model;

import java.util.ArrayList;
import java.util.List;

public class PageModel<T> {
	private int currentPage = 1;// 当前页数 
	public int totalPages = 0;// 总页数 
	private int pageSize = 50;// 每页显示数 
	private long totalRows = 0;// 总数据数 
	private int startNum = 0;// 开始记录 
	private int nextPage = 0;// 下一页 
	private int previousPage = 0;// 上一页 
	private boolean hasNextPage = false;// 是否有下一页 
	private boolean hasPreviousPage = false;// 是否有前一页 
	private List<T> datas ;//当前页的数据
	
	public PageModel(Integer currentPage, Integer pageSize, long totalRows) {
		super();
		this.currentPage = currentPage==null?1:currentPage;
		this.pageSize = pageSize==null?50:pageSize;
		this.totalRows = totalRows;
		initPageModel();
	}
	
	public void initPageModel(){
		if ((totalRows % pageSize) == 0) {
			totalPages = (int) (totalRows / pageSize);
		} else {
			totalPages = (int) (totalRows / pageSize + 1);
		}

		if (currentPage >= totalPages) {
			hasNextPage = false;
			currentPage = totalPages;
		} else {
			hasNextPage = true;
		}

		if (currentPage <= 1) {
			hasPreviousPage = false;
			currentPage = 1;
		} else {
			hasPreviousPage = true;
		}

		startNum = (currentPage - 1) * pageSize;

		nextPage = currentPage + 1;

		if (nextPage >= totalPages) {
			nextPage = totalPages;
		}

		previousPage = currentPage - 1;

		if (previousPage <= 1) {
			previousPage = 1;
		}
	}
	
	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(long totalRows) {
		this.totalRows = totalRows;
	}

	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public int getStartNum() {
		return startNum;
	}
	public void setStartNum(int startNum) {
		this.startNum = startNum;
	}
	public int getNextPage() {
		return nextPage;
	}
	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}
	public int getPreviousPage() {
		return previousPage;
	}
	public void setPreviousPage(int previousPage) {
		this.previousPage = previousPage;
	}
	public boolean isHasNextPage() {
		return hasNextPage;
	}
	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}
	public boolean isHasPreviousPage() {
		return hasPreviousPage;
	}
	public void setHasPreviousPage(boolean hasPreviousPage) {
		this.hasPreviousPage = hasPreviousPage;
	}
	public List<T> getDatas() {
		if(datas==null) datas = new ArrayList<T>();
		return datas;
	}
	public void setDatas(List<T> datas) {
		this.datas = datas;
	}
}
