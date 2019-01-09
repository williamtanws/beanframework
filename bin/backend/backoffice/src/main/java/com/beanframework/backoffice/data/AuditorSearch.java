package com.beanframework.backoffice.data;

import com.beanframework.common.domain.Auditor;

public class AuditorSearch extends Auditor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5313092618749920896L;
	private String searchAll;

	public String getSearchAll() {
		return searchAll;
	}

	public void setSearchAll(String searchAll) {
		this.searchAll = searchAll;
	}
}
