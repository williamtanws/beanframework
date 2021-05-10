package com.beanframework.internationalization.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.common.domain.GenericEntity;
import com.beanframework.internationalization.RegionConstants;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Table(name = RegionConstants.Table.REGION)
public class Region extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7791727441562748178L;

	public static final String NAME = "name";
	public static final String ACTIVE = "active";
	public static final String COUNTRY = "country";
	
	public Region() {
		super();
	}

	public Region(UUID uuid, String id, String name, Boolean active) {
		super();
		setUuid(uuid);
		setId(id);
		setName(name);
		setActive(active);
	}

	@Audited(withModifiedFlag = true)
	private String name;

	@Audited(withModifiedFlag = true)
	private Boolean active;

	@Audited(withModifiedFlag = true)
	@Column(name = "country_uuid", columnDefinition = "BINARY(16)")
	private UUID country;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public UUID getCountry() {
		return country;
	}

	public void setCountry(UUID country) {
		this.country = country;
	}
}
