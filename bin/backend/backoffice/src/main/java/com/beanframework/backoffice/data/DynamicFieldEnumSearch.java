package com.beanframework.backoffice.data;

import com.beanframework.dynamicfield.domain.DynamicFieldEnum;

public class DynamicFieldEnumSearch extends DynamicFieldEnum {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8143332459995856009L;
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
