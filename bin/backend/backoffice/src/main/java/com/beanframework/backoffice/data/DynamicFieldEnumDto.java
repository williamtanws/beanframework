package com.beanframework.backoffice.data;

import com.beanframework.common.data.GenericDto;

public class DynamicFieldEnumDto extends GenericDto {
	public static final String DYNAMIC_FIELD = "dynamicField";
	public static final String ENUM_GROUP = "enumGroup";
	public static final String NAME = "name";
	public static final String SORT = "sort";

	private DynamicFieldDto dynamicField;

	private String enumGroup;

	private String name;

	private Integer sort;

	public DynamicFieldDto getDynamicField() {
		return dynamicField;
	}

	public void setDynamicField(DynamicFieldDto dynamicField) {
		this.dynamicField = dynamicField;
	}

	public String getEnumGroup() {
		return enumGroup;
	}

	public void setEnumGroup(String enumGroup) {
		this.enumGroup = enumGroup;
	}

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
