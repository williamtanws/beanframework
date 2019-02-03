package com.beanframework.core.data;

import com.beanframework.common.data.GenericDto;

public class EnumerationDto extends GenericDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5096285286176721489L;
	public static final String NAME = "name";
	public static final String SORT = "sort";

	private String name;

	private String sort;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
}
