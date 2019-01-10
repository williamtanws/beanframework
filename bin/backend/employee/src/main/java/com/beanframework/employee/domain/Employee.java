package com.beanframework.employee.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Transient;

import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.employee.EmployeeConstants;
import com.beanframework.user.domain.User;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@DiscriminatorValue(EmployeeConstants.Discriminator.EMPLOYEE)
public class Employee extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = -444547554032025L;

	@Transient
	private String selected;

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

}
