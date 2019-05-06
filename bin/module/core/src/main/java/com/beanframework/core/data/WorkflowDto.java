package com.beanframework.core.data;

public class WorkflowDto extends GenericDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7727760714770195858L;

	private String name;
	private String classpath;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClasspath() {
		return classpath;
	}

	public void setClasspath(String classpath) {
		this.classpath = classpath;
	}

}
