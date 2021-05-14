package com.beanframework.logentry.event;

import com.beanframework.common.event.AbstractEvent;
import com.beanframework.logentry.LogentryType;

public class LogentryEvent extends AbstractEvent {

  /**
   * 
   */
  private static final long serialVersionUID = -3196311618663376663L;

  private LogentryType type;

  public LogentryEvent(Object source, LogentryType type) {
    super(source, type.toString());
    setType(type);
  }

  public LogentryEvent(Object source, LogentryType type, String message) {
    super(source, message);
    setType(type);
  }

  public LogentryType getType() {
    return type;
  }

  public void setType(LogentryType type) {
    this.type = type;
  }

}
