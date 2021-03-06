package com.beanframework.dynamicfield;

public class DynamicFieldConstants {

  public static interface Table {
    public static final String DYNAMIC_FIELD = "dynamicfield";
    public static final String DYNAMIC_FIELD_ENUMERATION_REL = "dynamicfield_enumerationrel";
  }

  public static interface Locale {
    public static final String ID_REQUIRED = "module.dynamicfield.id.required";
    public static final String ID_EXISTS = "module.dynamicfield.id.exists";
    public static final String UUID_NOT_EXISTS = "module.dynamicfield.uuid.notexists";;
  }
}
