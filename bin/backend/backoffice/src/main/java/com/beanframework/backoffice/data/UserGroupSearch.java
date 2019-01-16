package com.beanframework.backoffice.data;

import com.beanframework.user.domain.UserGroup;

public class UserGroupSearch extends UserGroup {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7214778984728422853L;
	private String searchAll;

	public String getSearchAll() {
		return searchAll;
	}

	public void setSearchAll(String searchAll) {
		this.searchAll = searchAll;
	}
	
	@Override
	public String toString() {
		return "UserGroupSearch [getSearchAll()=" + getSearchAll() + ", getName()=" + getName() + ", getId()=" + getId() + "]";
	}
}
