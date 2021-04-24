package com.beanframework.core.data;

import com.beanframework.common.data.GenericDto;

public class DynamicFieldSlotDto extends GenericDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6298813621287874479L;

	private String name;

	private Integer sort;

	private DynamicFieldDto dynamicField;

	private String selectedDynamicFieldUuid;

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

	public DynamicFieldDto getDynamicField() {
		return dynamicField;
	}

	public void setDynamicField(DynamicFieldDto dynamicField) {
		this.dynamicField = dynamicField;
	}

	public String getSelectedDynamicFieldUuid() {
		return selectedDynamicFieldUuid;
	}

	public void setSelectedDynamicFieldUuid(String selectedDynamicFieldUuid) {
		this.selectedDynamicFieldUuid = selectedDynamicFieldUuid;
	}

}
