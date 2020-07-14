package com.beanframework.internationalization.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.common.domain.GenericEntity;
import com.beanframework.internationalization.CurrencyConstants;

@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Table(name = CurrencyConstants.Table.CURRENCY)
public class Currency extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6640641390598966129L;

	public static final String NAME = "name";
	public static final String ACTIVE = "active";
	public static final String BASE = "base";
	public static final String CONVERTION = "convertion";
	public static final String DIGIT = "digit";
	public static final String SYMBOL = "symbol";

	@Audited(withModifiedFlag = true)
	private String name;
	
	@Audited(withModifiedFlag = true)
	private String active;
	
	@Audited(withModifiedFlag = true)
	private String base;
	
	@Audited(withModifiedFlag = true)
	private BigDecimal convertion;
	
	@Audited(withModifiedFlag = true)
	private BigDecimal digit;
	
	@Audited(withModifiedFlag = true)
	private String symbol;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public BigDecimal getConvertion() {
		return convertion;
	}

	public void setConvertion(BigDecimal convertion) {
		this.convertion = convertion;
	}

	public BigDecimal getDigit() {
		return digit;
	}

	public void setDigit(BigDecimal digit) {
		this.digit = digit;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

}
