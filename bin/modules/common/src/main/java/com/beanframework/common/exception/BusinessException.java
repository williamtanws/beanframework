package com.beanframework.common.exception;

public class BusinessException extends Exception {
  /**
   * 
   */
  private static final long serialVersionUID = -3725727021457801915L;

  public BusinessException(String message) {
    super(message);
  }

  public BusinessException(Throwable cause) {
    super(cause);
  }

  public BusinessException(String message, Throwable cause) {
    super(message, cause);
  }
}
