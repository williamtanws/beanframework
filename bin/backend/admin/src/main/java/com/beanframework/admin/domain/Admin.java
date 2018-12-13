package com.beanframework.admin.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Transient;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.admin.AdminConstants;
import com.beanframework.user.domain.User;

@Entity
@EntityListeners(AuditingEntityListener.class)
@DiscriminatorValue(AdminConstants.Discriminator.ADMIN)
public class Admin extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = -628677275018700297L;
	public static final String DOMAIN = "Admin";

	@Transient
	private String adminLoadInterceptor;

	public String getAdminLoadInterceptor() {
		return adminLoadInterceptor;
	}

	public void setAdminLoadInterceptor(String adminLoadInterceptor) {
		this.adminLoadInterceptor = adminLoadInterceptor;
	}

}
