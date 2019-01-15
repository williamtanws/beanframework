package com.beanframework.backoffice.data;

import com.beanframework.common.data.GenericDto;

public class CronjobDataDto extends GenericDto {
	private CronjobDto cronjob;

	private String name;

	private String value;

	public CronjobDto getCronjob() {
		return cronjob;
	}

	public void setCronjob(CronjobDto cronjob) {
		this.cronjob = cronjob;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
