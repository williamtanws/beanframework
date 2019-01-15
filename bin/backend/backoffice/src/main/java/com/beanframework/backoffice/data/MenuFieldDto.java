package com.beanframework.backoffice.data;

import com.beanframework.common.data.GenericDto;

public class MenuFieldDto extends GenericDto {

	private MenuDto menu;

	private DynamicFieldDto dynamicField;

	private String value;

	public MenuDto getMenu() {
		return menu;
	}

	public void setMenu(MenuDto menu) {
		this.menu = menu;
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

}
