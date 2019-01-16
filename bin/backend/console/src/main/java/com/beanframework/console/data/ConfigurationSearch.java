package com.beanframework.console.data;

import com.beanframework.configuration.domain.Configuration;

public class ConfigurationSearch extends Configuration {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7541038350063356234L;
	private String searchAll;

	public String getSearchAll() {
		return searchAll;
	}

	public void setSearchAll(String searchAll) {
		this.searchAll = searchAll;
	}

	@Override
	public String toString() {
		return "ConfigurationSearch [getSearchAll()=" + getSearchAll() + ", getValue()=" + getValue() + ", getId()=" + getId() + "]";
	}

}
