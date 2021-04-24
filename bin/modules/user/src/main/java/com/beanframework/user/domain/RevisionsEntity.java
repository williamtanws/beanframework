package com.beanframework.user.domain;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.common.domain.Auditor;
import com.beanframework.user.listener.EntityRevisionListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@RevisionEntity(EntityRevisionListener.class)
public class RevisionsEntity extends DefaultRevisionEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3217484900214041461L;

	@LastModifiedBy
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "lastmodifiedby_uuid")
	private Auditor lastModifiedBy;

	public Auditor getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(Auditor lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

}
