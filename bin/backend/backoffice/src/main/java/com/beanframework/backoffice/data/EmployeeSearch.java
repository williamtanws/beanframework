package com.beanframework.backoffice.data;

import com.beanframework.employee.domain.Employee;

public class EmployeeSearch extends Employee {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2994827428231731392L;
	private String searchAll;

	public String getSearchAll() {
		return searchAll;
	}

	public void setSearchAll(String searchAll) {
		this.searchAll = searchAll;
	}

}
