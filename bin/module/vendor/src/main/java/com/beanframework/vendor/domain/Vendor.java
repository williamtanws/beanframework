package com.beanframework.vendor.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;

import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.vendor.VendorConstants;
import com.beanframework.user.domain.User;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@DiscriminatorValue(VendorConstants.Discriminator.VENDOR)
public class Vendor extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4555310427686202883L;

}
