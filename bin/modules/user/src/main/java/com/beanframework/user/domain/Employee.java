package com.beanframework.user.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;

import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.user.EmployeeConstants;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@DiscriminatorValue(EmployeeConstants.Discriminator.EMPLOYEE)
public class Employee extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = -444547554032025L;

}
