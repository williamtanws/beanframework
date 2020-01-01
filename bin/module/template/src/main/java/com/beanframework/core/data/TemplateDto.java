package com.beanframework.core.data;

import com.beanframework.common.data.GenericDto;

public class TemplateDto extends GenericDto {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3245209665637084924L;
	public static final String NAME = "name";

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
