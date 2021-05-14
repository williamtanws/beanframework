package com.beanframework.common.utils;

public class UrlPathBuilder {

  private String str = "";

  public UrlPathBuilder append(Object obj) {
    return append(String.valueOf(obj));
  }

  public UrlPathBuilder append(String str) {
    this.str = this.str + str;
    return this;
  }

  public String toString() {
    return str;
  }
}
