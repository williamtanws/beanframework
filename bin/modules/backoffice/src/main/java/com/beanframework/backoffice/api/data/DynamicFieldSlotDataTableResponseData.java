package com.beanframework.backoffice.api.data;

import com.beanframework.core.data.DataTableResponseData;

public class DynamicFieldSlotDataTableResponseData extends DataTableResponseData {

	private String name;
	private Integer sort;

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

}
