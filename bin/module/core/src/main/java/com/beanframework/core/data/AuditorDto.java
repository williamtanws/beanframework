package com.beanframework.core.data;

import com.beanframework.common.data.GenericDto;

public class AuditorDto extends GenericDto {
	public static final String NAME = "name";
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
