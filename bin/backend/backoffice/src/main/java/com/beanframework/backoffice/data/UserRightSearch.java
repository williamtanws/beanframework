package com.beanframework.backoffice.data;

import com.beanframework.user.domain.UserRight;

public class UserRightSearch extends UserRight {

	/**
	 * 
	 */
	private static final long serialVersionUID = 235891293590559927L;
	private String searchAll;

	public String getSearchAll() {
		return searchAll;
	}

	public void setSearchAll(String searchAll) {
		this.searchAll = searchAll;
	}
}
