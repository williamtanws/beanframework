package com.beanframework.history.domain;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.common.domain.GenericDomain;
import com.beanframework.history.HistoryConstants;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = HistoryConstants.Table.HISTORY)
public class History extends GenericDomain {

	private static final long serialVersionUID = 5992760081038782486L;
	public static final String DOMAIN = "History";

	private String entity;
	private UUID entityUuid;

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public UUID getEntityUuid() {
		return entityUuid;
	}

	public void setEntityUuid(UUID entityUuid) {
		this.entityUuid = entityUuid;
	}
	

}
