package com.beanframework.user.domain;

import java.util.UUID;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;

import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.user.VendorConstants;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@DiscriminatorValue(VendorConstants.Discriminator.VENDOR)
public class Vendor extends User {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4555310427686202883L;
	
	public Vendor() {
		super();
	}
	
	public Vendor(UUID uuid, String id, String name) {
		super();
		setUuid(uuid);
		setId(id);
		setName(name);
	}

}
