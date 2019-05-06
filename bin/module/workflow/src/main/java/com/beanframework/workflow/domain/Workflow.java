package com.beanframework.workflow.domain;

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
	public static final String CLASSPATH = "classpath";

	@Audited(withModifiedFlag = true)
	private String name;

	@Audited(withModifiedFlag = true)
	private String classpath;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClasspath() {
		return classpath;
	}

	public void setClasspath(String classpath) {
		this.classpath = classpath;
	}

}
