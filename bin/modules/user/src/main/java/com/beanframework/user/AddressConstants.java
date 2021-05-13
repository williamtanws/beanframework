package com.beanframework.user;

public class AddressConstants {

  public static interface Table {
    public static final String ADDRESS = "address";
  }

  public static interface Locale {
    public static final String ID_REQUIRED = "module.address.id.required";
    public static final String ID_EXISTS = "module.address.id.exists";
    public static final String UUID_NOT_EXISTS = "module.address.uuid.notexists";;
  }
}
