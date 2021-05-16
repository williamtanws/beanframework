package com.beanframework.user.event;

import com.beanframework.logentry.LogentryType;
import com.beanframework.logentry.event.LogentryEvent;

public class AuthenticationEvent extends LogentryEvent {

  /**
   * 
   */
  private static final long serialVersionUID = 6402615453169155659L;

  public AuthenticationEvent(Object source, LogentryType type, String message) {
    super(source, type, message);
  }
}
