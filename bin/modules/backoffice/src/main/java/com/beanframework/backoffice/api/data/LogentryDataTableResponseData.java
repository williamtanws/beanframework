package com.beanframework.backoffice.api.data;

import com.beanframework.core.data.DataTableResponseData;
import com.beanframework.logentry.LogentryType;

public class LogentryDataTableResponseData extends DataTableResponseData {

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
