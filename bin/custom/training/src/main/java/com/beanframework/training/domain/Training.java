package com.beanframework.training.domain;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.common.domain.GenericEntity;
import com.beanframework.training.TrainingConstants;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Table(name = TrainingConstants.Table.TRAINING)
public class Training extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5147260766373308723L;

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
