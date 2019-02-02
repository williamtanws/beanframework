package com.beanframework.backoffice.data;

import com.beanframework.common.data.DataTableResponseData;

public class EnumerationDataResponse extends DataTableResponseData {

	private Integer sort;
	private String enumGroup;

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getEnumGroup() {
		return enumGroup;
	}

	public void setEnumGroup(String enumGroup) {
		this.enumGroup = enumGroup;
	}

}
