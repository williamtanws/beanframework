package com.beanframework.dynamicfield;

public enum DynamicFieldType {
  INTEGER("Integer"), FLOATING_POINT("Floating Point"), TEXT("Text"), TEXTAREA("Text Area"), EDITOR(
      "Editor"), SELECT("Select"), DATE("Date"), BOOLEAN("Boolean");

  private String type;

  DynamicFieldType(String type) {
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
