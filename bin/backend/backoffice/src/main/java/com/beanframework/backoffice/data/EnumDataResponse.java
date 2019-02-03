package com.beanframework.backoffice.data;

import com.beanframework.common.data.DataTableResponseData;

public class EnumDataResponse extends DataTableResponseData {

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
