package com.beanframework.core.data;

public class UserPermissionFieldDto extends GenericDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2920029811939870923L;

	private UserPermissionDto userPermission;

	private DynamicFieldDto dynamicField;

	private String value;

	private Integer sort;

	public UserPermissionDto getUserPermission() {
		return userPermission;
	}

	public void setUserPermission(UserPermissionDto userPermission) {
		this.userPermission = userPermission;
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
