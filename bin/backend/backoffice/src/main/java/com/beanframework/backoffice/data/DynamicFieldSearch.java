package com.beanframework.backoffice.data;

import com.beanframework.dynamicfield.domain.DynamicField;

public class DynamicFieldSearch extends DynamicField {

	/**
	 * 
	 */
	private static final long serialVersionUID = 369849270149391977L;
	private String searchAll;

	public String getSearchAll() {
		return searchAll;
	}

	public void setSearchAll(String searchAll) {
		this.searchAll = searchAll;
	}
	
	@Override
	public String toString() {
		return "DynamicFieldSearch [getSearchAll()=" + getSearchAll() + ", getName()=" + getName() + ", getId()=" + getId() + "]";
	}
}
