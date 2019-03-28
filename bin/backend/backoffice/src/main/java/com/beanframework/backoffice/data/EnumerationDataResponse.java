package com.beanframework.backoffice.data;

import com.beanframework.core.data.DataTableResponseData;

public class EnumerationDataResponse extends DataTableResponseData {

	private String sort;
	private String enumGroup;

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getEnumGroup() {
		return enumGroup;
	}

	public void setEnumGroup(String enumGroup) {
		this.enumGroup = enumGroup;
	}

}
