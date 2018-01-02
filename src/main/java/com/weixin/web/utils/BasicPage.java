package com.weixin.web.utils;

import java.util.List;

/**
 * 基础页面
 * 
 * @author Administrator
 *
 */
public class BasicPage<T> {

	// 分页开始
	private int begin;
	// 页面显示的大小
	private int pageSize;
	// 当前页
	private int currentPageNumber;
	// 总页数
	private int pageTotal;
	// 查询出的总记录数
	private int total;

	private List<T> rows;

	public BasicPage() {
		super();
		this.begin = 0;
		this.pageSize = 10;
	}

	public int getBegin() {
		return begin;
	}

	public void setBegin() {
	int currentPageNumber = getCurrentPageNumber();
	int pageSize = getPageSize();
	int begin = (currentPageNumber-1)*pageSize;
	this.begin = begin;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		
	}

	public int getCurrentPageNumber() {
		return currentPageNumber;
	}

	public void setCurrentPageNumber(int currentPageNumber) {
		this.currentPageNumber = currentPageNumber;
		setBegin();
	}

	public int getPageTotal() {
		return pageTotal;
	}

	public void setPageTotal() {
		int total = getTotal();
		int pageSize = getPageSize();
		if (total % pageSize == 0) {
			this.pageTotal = total / pageSize;
		} else {
			this.pageTotal = total / pageSize + 1;
		}
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
		setPageTotal();
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

}
