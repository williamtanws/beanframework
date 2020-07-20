package com.beanframework.backoffice.data;

import com.beanframework.core.data.DataTableResponseData;

public class CronjobDataTableResponseData extends DataTableResponseData {

	private String jobGroup;
	private String name;
	private String status;

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
