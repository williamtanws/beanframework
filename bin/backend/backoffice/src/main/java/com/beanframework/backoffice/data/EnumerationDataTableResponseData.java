package com.beanframework.backoffice.data;

import com.beanframework.core.data.DataTableResponseData;

public class EnumerationDataTableResponseData extends DataTableResponseData {

	private Integer sort;
	private String enumGroup;
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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
