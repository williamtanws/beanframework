package com.beanframework.configuration.domain;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.common.domain.GenericDomain;
import com.beanframework.configuration.ConfigurationConstants;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = ConfigurationConstants.Table.CONFIGURATION)
public class Configuration extends GenericDomain {

	private static final long serialVersionUID = 2129119893141952037L;
	public static final String VALUE = "value";
	
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
