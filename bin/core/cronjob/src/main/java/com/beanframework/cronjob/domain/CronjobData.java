package com.beanframework.cronjob.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.common.domain.GenericDomain;
import com.beanframework.cronjob.CronjobConstants;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = CronjobConstants.Table.CRONJOB_DATA)
public class CronjobData extends GenericDomain {

	private static final long serialVersionUID = 5142597586346258761L;
	public static final String NAME = "name";
	public static final String VALUE = "value";
	public static final String CRONJOB = "cronjob";

	private String name;
	private String value;

	@ManyToOne
	@JoinColumn(name = "cronjob_uuid")
	private Cronjob cronjob;

	@CreatedDate
	@Column(updatable = false)
	private Date createdDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Cronjob getCronjob() {
		return cronjob;
	}

	public void setCronjob(Cronjob cronjob) {
		this.cronjob = cronjob;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

}
