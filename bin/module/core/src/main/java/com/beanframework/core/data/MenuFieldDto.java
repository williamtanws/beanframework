package com.beanframework.core.data;

public class MenuFieldDto extends GenericDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3192301047685937164L;

	private DynamicFieldDto dynamicField;

	private String value;

	private Integer sort;

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
