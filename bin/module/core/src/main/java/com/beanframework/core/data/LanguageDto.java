package com.beanframework.core.data;

import com.beanframework.common.data.GenericDto;

public class LanguageDto extends GenericDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3610994073724879265L;
	public static final String NAME = "name";
	public static final String ACTIVE = "active";
	public static final String SORT = "sort";

	private String name;

	private Boolean active;

	private String sort;

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

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

}
