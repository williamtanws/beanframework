package com.beanframework.core.data;

import com.beanframework.common.data.GenericDto;
import com.beanframework.logentry.LogentryType;

public class LogentryDto extends GenericDto {

  /**
   * 
   */
  private static final long serialVersionUID = 1293632832688329805L;
  private LogentryType type;
  private String message;

  public LogentryType getType() {
    return type;
  }

  public void setType(LogentryType type) {
    this.type = type;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

}
