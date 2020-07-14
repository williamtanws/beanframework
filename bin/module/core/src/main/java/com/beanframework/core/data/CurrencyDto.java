package com.beanframework.core.data;

import java.math.BigDecimal;

import com.beanframework.common.data.GenericDto;

public class CurrencyDto extends GenericDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6539563538992591700L;
	private String name;
	private String active;
	private String base;
	private BigDecimal convertion;
	private BigDecimal digit;
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
