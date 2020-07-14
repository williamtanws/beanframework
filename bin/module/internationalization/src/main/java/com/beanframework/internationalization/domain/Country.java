package com.beanframework.internationalization.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
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

	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	@Audited(withModifiedFlag = true)
	@Cascade({ CascadeType.REFRESH })
	@OneToMany(orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "country")
	private List<Region> regions = new ArrayList<Region>();

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

	public List<Region> getRegions() {
		return regions;
	}

	public void setRegions(List<Region> regions) {
		this.regions = regions;
	}

}
