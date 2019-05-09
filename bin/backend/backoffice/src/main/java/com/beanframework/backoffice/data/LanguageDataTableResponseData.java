package com.beanframework.backoffice.data;

import com.beanframework.core.data.DataTableResponseData;

public class LanguageDataTableResponseData extends DataTableResponseData {

	private Boolean active;
	private Integer sort;
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}
