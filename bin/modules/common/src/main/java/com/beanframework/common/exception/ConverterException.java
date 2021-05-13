package com.beanframework.common.exception;

import com.beanframework.common.converter.Converter;

public class ConverterException extends BusinessException {

  /**
   * 
   */
  private static final long serialVersionUID = -4484552745918750990L;
  private Converter converter;

  public ConverterException(String message) {
    this(message, (Throwable) null, (Converter) null);
  }

  public ConverterException(String message, Throwable cause) {
    this(message, cause, (Converter) null);
  }

  public ConverterException(String message, Converter converter) {
    this(message, (Throwable) null, converter);
  }

  public ConverterException(String message, Throwable cause, Converter converter) {
    super(message, cause);
    this.setConverter(converter);
  }

  public Converter getConverter() {
    return converter;
  }

  public void setConverter(Converter converter) {
    this.converter = converter;
  }

  public String getMessage() {
    return "[" + this.converter + "]:" + super.getMessage();
  }
}
