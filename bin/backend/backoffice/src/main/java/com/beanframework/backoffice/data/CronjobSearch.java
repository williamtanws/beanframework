package com.beanframework.backoffice.data;

import com.beanframework.cronjob.domain.Cronjob;

public class CronjobSearch extends Cronjob {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7307826294746220674L;
	private String searchAll;

	public String getSearchAll() {
		return searchAll;
	}

	public void setSearchAll(String searchAll) {
		this.searchAll = searchAll;
	}

	@Override
	public String toString() {
		return "CronjobSearch [getSearchAll()=" + getSearchAll() + ", getJobName()=" + getJobName() + ", getId()=" + getId() + "]";
	}

}
