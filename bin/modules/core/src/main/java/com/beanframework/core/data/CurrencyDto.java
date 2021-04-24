package com.beanframework.core.data;

import com.beanframework.common.data.GenericDto;

public class CurrencyDto extends GenericDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6539563538992591700L;
	private String name;
	private Boolean active;
	private Boolean base;
	private Double conversion;
	private Integer digit;
	private String symbol;

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

	public Boolean getBase() {
		return base;
	}

	public void setBase(Boolean base) {
		this.base = base;
	}

	public Double getConversion() {
		return conversion;
	}

	public void setConversion(Double conversion) {
		this.conversion = conversion;
	}

	public Integer getDigit() {
		return digit;
	}

	public void setDigit(Integer digit) {
		this.digit = digit;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

}
