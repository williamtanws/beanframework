package com.beanframework.employee.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.employee.EmployeeConstants;
import com.beanframework.user.domain.User;

@Entity
@EntityListeners(AuditingEntityListener.class)
@DiscriminatorValue(EmployeeConstants.Discriminator.EMPLOYEE)
public class Employee extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = -444547554032025L;
	public static final String MODEL = "Employee";
	public static final String NAME = "name";
	public static final String PICTURE = "picture";

	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
