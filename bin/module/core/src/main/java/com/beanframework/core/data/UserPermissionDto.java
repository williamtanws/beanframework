package com.beanframework.core.data;

import java.util.ArrayList;
import java.util.List;

import com.beanframework.common.data.GenericDto;

public class UserPermissionDto extends GenericDto {
	public static final String NAME = "name";
	public static final String USER_PERMISSION_FIELD = "userPermissionField";
	public static final String SORT = "sort";
	public static final String FIELDS = "fields";
	
	private String name;

	private List<UserPermissionFieldDto> fields = new ArrayList<UserPermissionFieldDto>();

	private Integer sort;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<UserPermissionFieldDto> getFields() {
		return fields;
	}

	public void setFields(List<UserPermissionFieldDto> fields) {
		this.fields = fields;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}