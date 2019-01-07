package com.beanframework.cronjob.domain;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.common.domain.GenericDomain;
import com.beanframework.cronjob.CronjobConstants;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Table(name = CronjobConstants.Table.CRONJOB_DATA)
public class CronjobData extends GenericDomain {

	private static final long serialVersionUID = 5142597586346258761L;
	public static final String NAME = "name";
	public static final String VALUE = "value";
	public static final String CRONJOB = "cronjob";

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne
	@JoinColumn(name = "cronjob_uuid")
	private Cronjob cronjob;

	@Audited(withModifiedFlag = true)
	private String name;

	@Audited(withModifiedFlag = true)
	private String value;

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
}
