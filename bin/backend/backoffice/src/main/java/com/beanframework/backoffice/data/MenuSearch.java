package com.beanframework.backoffice.data;

import com.beanframework.menu.domain.Menu;

public class MenuSearch extends Menu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6701881989757229791L;
	private String searchAll;

	public String getSearchAll() {
		return searchAll;
	}

	public void setSearchAll(String searchAll) {
		this.searchAll = searchAll;
	}
	
	@Override
	public String toString() {
		return "MenuSearch [getSearchAll()=" + getSearchAll() + ", getName()=" + getName() + ", getId()=" + getId() + "]";
	}
}
