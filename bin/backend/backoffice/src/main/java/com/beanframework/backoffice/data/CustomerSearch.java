package com.beanframework.backoffice.data;

import com.beanframework.customer.domain.Customer;

public class CustomerSearch extends Customer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3994276317460096414L;
	private String searchAll;

	public String getSearchAll() {
		return searchAll;
	}

	public void setSearchAll(String searchAll) {
		this.searchAll = searchAll;
	}

}
