package com.beanframework.backoffice.data;

import com.beanframework.email.domain.Email;

public class EmailSearch extends Email {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2095184379346330872L;
	private String searchAll;

	public String getSearchAll() {
		return searchAll;
	}

	public void setSearchAll(String searchAll) {
		this.searchAll = searchAll;
	}
	
	@Override
	public String toString() {
		return "EmailSearch [getSearchAll()=" + getSearchAll() + ", getName()=" + getName() + ", getSubject()=" + getSubject() + "]";
	}
}
