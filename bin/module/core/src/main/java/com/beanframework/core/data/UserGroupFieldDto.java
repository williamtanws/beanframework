package com.beanframework.core.data;

import com.beanframework.common.data.GenericDto;

public class UserGroupFieldDto extends GenericDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6527945805106422096L;

	private UserGroupDto userGroup;

	private DynamicFieldDto dynamicField;

	private String value;

	private Integer sort;

	public UserGroupDto getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(UserGroupDto userGroup) {
		this.userGroup = userGroup;
	}

	public DynamicFieldDto getDynamicField() {
		return dynamicField;
	}

	public void setDynamicField(DynamicFieldDto dynamicField) {
		this.dynamicField = dynamicField;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}
