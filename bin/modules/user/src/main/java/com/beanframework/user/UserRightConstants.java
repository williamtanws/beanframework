package com.beanframework.user;

public final class UserRightConstants {

  private UserRightConstants() {
    throw new AssertionError();
  }

  public interface Table {
    public static final String USER_RIGHT = "userright";
    public static final String USER_RIGHT_ATTRIBUTE = "userright_attribute";
  }

  public interface Locale {
    public static final String ID_REQUIRED = "module.userright.id.required";
    public static final String ID_EXISTS = "module.userright.id.exists";
    public static final String UUID_NOT_EXISTS = "module.userright.uuid.notexists";;
  }

  public static final String CONFIGURATION_DYNAMIC_FIELD_TEMPLATE =
      "userright.dynamicfield.template";
}
