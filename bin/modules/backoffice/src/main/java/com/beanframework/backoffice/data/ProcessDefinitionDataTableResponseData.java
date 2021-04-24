package com.beanframework.backoffice.data;

import com.beanframework.core.data.DataTableResponseData;

public class ProcessDefinitionDataTableResponseData extends DataTableResponseData {
	
	private String name;
	private String deploymentId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDeploymentId() {
		return deploymentId;
	}

	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}
}
