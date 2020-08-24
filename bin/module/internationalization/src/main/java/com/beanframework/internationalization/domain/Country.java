package com.beanframework.internationalization.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.common.domain.GenericEntity;
import com.beanframework.internationalization.CountryConstants;

@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Table(name = CountryConstants.Table.COUNTRY)
public class Country extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7791727441562748178L;

	public static final String NAME = "name";
	public static final String ACTIVE = "active";
	public static final String REGIONS = "regions";

	@Audited(withModifiedFlag = true)
	private String name;

	@Audited(withModifiedFlag = true)
	private Boolean active;

	@Audited(withModifiedFlag = true)
	@ElementCollection
	@CollectionTable(name = CountryConstants.Table.COUNTRY_REGION_REL, joinColumns = @JoinColumn(name = "country_uuid"))
	@Column(name="region_uuid")
	private List<UUID> regions = new ArrayList<UUID>();

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

	public List<UUID> getRegions() {
		return regions;
	}

	public void setRegions(List<UUID> regions) {
		this.regions = regions;
	}

}
