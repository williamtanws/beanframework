package com.beanframework.core.data;

import com.beanframework.common.data.GenericDto;
import com.beanframework.dynamicfield.domain.DynamicField;

public class DynamicFieldSlotDto extends GenericDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6298813621287874479L;
	private Integer sort;
	private DynamicField dynamicField;

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public DynamicField getDynamicField() {
		return dynamicField;
	}

	public void setDynamicField(DynamicField dynamicField) {
		this.dynamicField = dynamicField;
	}

}
