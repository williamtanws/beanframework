package com.beanframework.cms.domain;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.common.domain.GenericEntity;
import com.beanframework.workflow.WorkflowConstants;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Table(name = WorkflowConstants.Table.WORKFLOW)
public class Workflow extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
