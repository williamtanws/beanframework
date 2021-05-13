package com.beanframework.core.csv;

import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseBool;
import org.supercsv.cellprocessor.ParseDouble;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.Trim;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import com.beanframework.common.data.GenericCsv;

public class CurrencyCsv extends GenericCsv {

  private String name;
  private Boolean active;
  private Boolean base;
  private Double conversion;
  private Integer digit;
  private String symbol;

  public static CellProcessor[] getUpdateProcessors() {
    final CellProcessor[] processors = new CellProcessor[] { //
        new Optional(new Trim()), // ModeType
        new NotNull(new Trim()), // id
        new Optional(new Trim()), // name
        new Optional(new Trim(new ParseBool())), // active
        new Optional(new Trim(new ParseBool())), // base
        new Optional(new Trim(new ParseDouble())), // conversion
        new Optional(new Trim(new ParseInt())), // digit
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

  @Override
  public String toString() {
    return "CurrencyCsv [name=" + name + ", active=" + active + ", base=" + base + ", conversion="
        + conversion + ", digit=" + digit + ", symbol=" + symbol + "]";
  }

}
