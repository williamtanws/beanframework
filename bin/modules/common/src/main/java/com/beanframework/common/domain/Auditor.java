package com.beanframework.common.domain;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.common.CommonConstants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "uuid", "createdDate", "createdBy", "lastModifiedDate", "lastModifiedBy" })
@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Table(name = CommonConstants.Table.AUDITOR)
public class Auditor extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2180422049651244927L;
	public static final String NAME = "name";

	@Audited(withModifiedFlag = true)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
