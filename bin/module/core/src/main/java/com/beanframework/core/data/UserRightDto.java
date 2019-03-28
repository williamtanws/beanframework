package com.beanframework.core.data;

import java.util.ArrayList;
import java.util.List;

public class UserRightDto extends GenericDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3879059423333980271L;
	public static final String NAME = "name";
	public static final String FIELDS = "fields";
	public static final String SORT = "sort";

	private String name;

	private List<UserRightFieldDto> fields = new ArrayList<UserRightFieldDto>();

	private Integer sort;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<UserRightFieldDto> getFields() {
		return fields;
	}

	public void setFields(List<UserRightFieldDto> fields) {
		this.fields = fields;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}
