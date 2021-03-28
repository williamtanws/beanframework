package com.beanframework.core.data;

import com.beanframework.common.data.GenericDto;

public class WorkflowDto extends GenericDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7727760714770195858L;

	private String name;
	private String classpath;
	private String deploymentId;

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

	public String getDeploymentId() {
		return deploymentId;
	}

	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}

}
