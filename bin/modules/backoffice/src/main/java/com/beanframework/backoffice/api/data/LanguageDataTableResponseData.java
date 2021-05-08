package com.beanframework.backoffice.api.data;

import com.beanframework.core.data.DataTableResponseData;

public class LanguageDataTableResponseData extends DataTableResponseData {
	
	private String name;
	private Boolean active;
	private Integer sort;

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
