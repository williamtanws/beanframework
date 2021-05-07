package com.beanframework.core.data;

import java.util.ArrayList;
import java.util.List;

import com.beanframework.common.data.GenericDto;

public class UserPermissionDto extends GenericDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3442255085071884522L;
	public static final String NAME = "name";
	public static final String FIELDS = "fields";
	public static final String SORT = "sort";

	private String name;

	private List<UserPermissionAttributeDto> attributes = new ArrayList<UserPermissionAttributeDto>();

	private Integer sort;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<UserPermissionAttributeDto> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<UserPermissionAttributeDto> attributes) {
		this.attributes = attributes;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}
