package com.beanframework.customer.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;

import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.customer.CustomerConstants;
import com.beanframework.user.domain.User;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@DiscriminatorValue(CustomerConstants.Discriminator.CUSTOMER)
public class Customer extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = -628677275018700297L;
	public static final String MODEL = "Customer";
	public static final String NAME = "name";

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
