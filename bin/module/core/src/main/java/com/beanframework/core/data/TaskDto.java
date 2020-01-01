package com.beanframework.core.data;

import com.beanframework.common.data.GenericDto;

public class TaskDto extends GenericDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1601181367971257275L;
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
