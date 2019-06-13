package com.beanframework.customer.domain;

import org.springframework.cache.annotation.Cacheable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.customer.CustomerConstants;
import com.beanframework.user.domain.User;

@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@DiscriminatorValue(CustomerConstants.Discriminator.CUSTOMER)
public class Customer extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = -628677275018700297L;
}
