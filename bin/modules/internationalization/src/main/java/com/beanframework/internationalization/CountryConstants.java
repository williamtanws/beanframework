package com.beanframework.internationalization;

public class CountryConstants {

  public static interface Table {
    public static final String COUNTRY = "country";
  }

  public static interface Locale {
    public static final String ID_REQUIRED = "module.country.id.required";
    public static final String ID_EXISTS = "module.country.id.exists";
    public static final String UUID_NOT_EXISTS = "module.country.uuid.notexists";;
  }
}
