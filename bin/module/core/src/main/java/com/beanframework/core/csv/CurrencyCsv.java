package com.beanframework.core.csv;

import java.math.BigDecimal;

import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseBigDecimal;
import org.supercsv.cellprocessor.ParseBool;
import org.supercsv.cellprocessor.Trim;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;

import com.beanframework.common.data.AbstractCsv;

public class CurrencyCsv extends AbstractCsv {

	private String name;
	private Boolean active;
	private String base;
	private BigDecimal convertion;
	private BigDecimal digit;
	private String symbol;

	public static CellProcessor[] getUpdateProcessors() {
		final CellProcessor[] processors = new CellProcessor[] { //
				new Optional(new Trim()), // ModeType
				new NotNull(new Trim()), // id
				new Optional(new Trim()), // name
				new Optional(new Trim(new ParseBool())), // active
				new Optional(new Trim()), // base
				new Optional(new Trim(new ParseBigDecimal())), // convertion
				new Optional(new Trim(new ParseBigDecimal())), // digit
				new Optional(new Trim()) // symbol
		};

		return processors;
	}

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

	@Override
	public String toString() {
		return "CurrencyCsv [name=" + name + ", active=" + active + ", base=" + base + ", convertion=" + convertion + ", digit=" + digit + ", symbol=" + symbol + "]";
	}

}
