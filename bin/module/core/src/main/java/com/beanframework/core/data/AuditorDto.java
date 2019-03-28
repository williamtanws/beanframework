package com.beanframework.core.data;

public class AuditorDto extends GenericDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9218549753097868735L;

	public static final String NAME = "name";

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
