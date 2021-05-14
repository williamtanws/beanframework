package com.beanframework.logentry;

public enum LogentryType {
  CREATE("Create"), READ("Read"), UPDATE("Update"), DELETE("Delete"), AUTH("Auth");

  private String type;

  LogentryType(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }

  @Override
  public String toString() {
    return type;
  }
}
