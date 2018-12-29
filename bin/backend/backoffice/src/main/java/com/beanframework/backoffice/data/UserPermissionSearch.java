package com.beanframework.backoffice.data;

import com.beanframework.user.domain.UserPermission;

public class UserPermissionSearch extends UserPermission {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8790169671366545487L;
	private String searchAll;

	public String getSearchAll() {
		return searchAll;
	}

	public void setSearchAll(String searchAll) {
		this.searchAll = searchAll;
	}
}
