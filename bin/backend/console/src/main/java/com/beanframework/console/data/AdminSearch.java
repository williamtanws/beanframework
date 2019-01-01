package com.beanframework.console.data;

import com.beanframework.admin.domain.Admin;

public class AdminSearch extends Admin {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5636156261477339317L;
	private String searchAll;

	public String getSearchAll() {
		return searchAll;
	}

	public void setSearchAll(String searchAll) {
		this.searchAll = searchAll;
	}
}
