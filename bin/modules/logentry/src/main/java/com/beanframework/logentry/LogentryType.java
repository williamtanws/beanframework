package com.beanframework.logentry;

public enum LogentryType {
  CREATE("Create"), READ("Read"), UPDATE("Update"), DELETE("Delete"), LOGIN("Login"), LOGOUT(
      "Logout");

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
