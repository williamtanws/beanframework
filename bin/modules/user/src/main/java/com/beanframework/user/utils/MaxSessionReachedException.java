package com.beanframework.user.utils;

import org.springframework.security.core.AuthenticationException;

public class MaxSessionReachedException extends AuthenticationException {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public MaxSessionReachedException(String msg) {
    super(msg);
  }

  public MaxSessionReachedException(String msg, Throwable t) {
    super(msg, t);
  }
}
