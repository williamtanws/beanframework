package com.beanframework.common.converter;

import org.springframework.core.Ordered;

public class ConverterMapping implements Ordered {
  private String typeCode;
  private Converter converter;
  private int order = Integer.MAX_VALUE;

  public ConverterMapping() {}

  public ConverterMapping(String typeCode, Converter converter) {
    this.typeCode = typeCode;
    this.converter = converter;
  }

  public String getTypeCode() {
    return typeCode;
  }

  public void setTypeCode(String typeCode) {
    this.typeCode = typeCode;
  }

  public Converter getConverter() {
    return converter;
  }

  public void setConverter(Converter converter) {
    this.converter = converter;
  }

  public final void setOrder(int order) {
    this.order = order;
  }

  public final int getOrder() {
    return this.order;
  }

}
