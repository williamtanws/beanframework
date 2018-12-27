package com.beanframework.backoffice.data;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;

import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.backoffice.listener.EntityRevisionListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@RevisionEntity(EntityRevisionListener.class)
public class RevisionsEntity extends DefaultRevisionEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3217484900214041461L;
	
	@LastModifiedBy
	private String lastModifiedBy;

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

}
