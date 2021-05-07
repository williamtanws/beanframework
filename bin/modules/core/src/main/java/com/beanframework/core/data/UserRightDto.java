package com.beanframework.core.data;

import java.util.ArrayList;
import java.util.List;

import com.beanframework.common.data.GenericDto;

public class UserRightDto extends GenericDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3879059423333980271L;
	public static final String NAME = "name";
	public static final String FIELDS = "fields";
	public static final String SORT = "sort";

	private String name;

	private List<UserRightAttributeDto> attributes = new ArrayList<UserRightAttributeDto>();

	private Integer sort;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<UserRightAttributeDto> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<UserRightAttributeDto> attributes) {
		this.attributes = attributes;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}
