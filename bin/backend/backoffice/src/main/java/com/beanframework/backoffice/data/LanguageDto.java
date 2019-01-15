package com.beanframework.backoffice.data;

import com.beanframework.common.data.GenericDto;

public class LanguageDto extends GenericDto {
	public static final String NAME = "name";
	public static final String ACTIVE = "active";
	public static final String SORT = "sort";

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
